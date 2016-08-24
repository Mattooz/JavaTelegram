package me.niccolomattei.api.telegram.configuration;

import me.niccolomattei.api.telegram.Bot;
import me.niccolomattei.api.telegram.json.JSONPrettyPrinter;
import me.niccolomattei.api.telegram.serialization.ISerializable;
import org.json.JSONArray;
import org.json.JSONObject;

import java.io.*;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Set;

public class Configuration {

	public static final String defaultPath = "bot/config/";
	public static final File defaultPathFile = new File("bot/config/");

	private File configFile;
	private JSONObject configuration;

	public static boolean generateDefault(String name) {
		File f = new File(defaultPathFile, name);
		return generateDefault(f);
	}

	public static boolean generateDefault(File path) {
		return generateDefault(path, defaultPathFile);
	}

	public static boolean generateDefault(File pathToFile, File mkFolders) {
		FileWriter writer = null;
		if (!pathToFile.exists()) {
			mkFolders.mkdirs();
			try {
				pathToFile.createNewFile();

				writer = new FileWriter(pathToFile);
				writer.write("{}");
			} catch (IOException e) {
				e.printStackTrace();
			} finally {
				try {
					writer.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
			return true;
		} else
			return false;
	}

	public Configuration(String configPath) {
		BufferedReader reader;
		File file = new File(configPath);
		StringBuilder builder = new StringBuilder();
		if (!file.exists())
			throw new ConfigurationException("Config file does not exist!");
		if (file.isDirectory())
			throw new ConfigurationException("Config file cannot be a directory!");
		this.configFile = file;
		try {
			reader = new BufferedReader(new FileReader(file));
			String read;
			while ((read = reader.readLine()) != null) {
				builder.append(read);
			}
			reader.close();
			configuration = new JSONObject(builder.toString());
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	private Object obt(String key, JSONObject parent) {
		if (key.contains(".")) {
			String[] names = key.split("\\.");
			Object object = null;
			for (int i = 0; i < names.length; i++) {
				if ((i + 1) != names.length) {
					if (i == 0) {
						if (parent.has(names[i]))
							object = parent.get(names[i]);
						else
							break;
					} else {
						if (!(object instanceof JSONObject))
							break;
						if (((JSONObject) object).has(names[i]))
							object = ((JSONObject) object).get(names[i]);
						else
							break;
					}
				} else {
					if ((object instanceof JSONObject)) {
						JSONObject json = (JSONObject) object;
						if (json.has(names[i])) {
							return json.get(names[i]);
						} else
							break;
					} else
						break;
				}
			}
		} else {
			if (configuration.has(key))
				return configuration.get(key);
		}
		return null;
	}

	public Object get(String key) {
		return this.obt(key, configuration);
	}

	public <E> E get(Class<E> expected, String key) {
		try {
			return expected.cast(this.get(key));
		} catch (ClassCastException e) {
			Bot.currentBot.getLogger().severe("Cannot cast object to " + expected.getSimpleName() + "!");
			return null;
		}
	}

	public String getString(String key) {
		try {
			return (String) this.get(key);
		} catch (ClassCastException e) {
			Bot.currentBot.getLogger().severe("Cannot cast object to string!");
			return null;
		}
	}

	public int getInt(String key) {
		try {
			return (int) this.get(key);
		} catch (ClassCastException e) {
			Bot.currentBot.getLogger().severe("Cannot cast object to int!");
			return 0;
		}
	}

	public double getDouble(String key) {
		try {
			return (double) this.get(key);
		} catch (ClassCastException e) {
			Bot.currentBot.getLogger().severe("Cannot cast object to double!");
			return 0;
		}
	}

	public long getLong(String key) {
		try {
			return (long) this.get(key);
		} catch (ClassCastException e) {
			Bot.currentBot.getLogger().severe("Cannot cast object to long!");
			return 0;
		}
	}

	public short getShort(String key) {
		try {
			return (short) this.get(key);
		} catch (ClassCastException e) {
			Bot.currentBot.getLogger().severe("Cannot cast object to short!");
			return 0;
		}
	}

	public float getFloat(String key) {
		try {
			return (float) this.get(key);
		} catch (ClassCastException e) {
			Bot.currentBot.getLogger().severe("Cannot cast object to float!");
			return 0;
		}
	}

	public byte getByte(String key) {
		try {
			return (byte) this.get(key);
		} catch (ClassCastException e) {
			Bot.currentBot.getLogger().severe("Cannot cast object to byte!");
			return 0;
		}
	}

	/**
	 * Gets a generic object collection.
	 * If collection does not exists, will return null.
	 * 
	 * @param key the key to the list
	 * @return
	 */
	public Collection<Object> getCollection(String key) {
		Collection<Object> res = new ArrayList<>();

		if (contains(key)) {
			if (get(key) instanceof JSONArray)
				((JSONArray) get(key)).forEach(res::add);

			return res;
		} else {
			return null;
		}
	}
	
	
	/**
	 * Returns a collection with the type you choose.
	 * 
	 * @param expected the type of the collection.
	 * @param key the location of the collection.
	 * @return the collection if casted, will return an empty array on cast failure.
	 */
	public <E> Collection<E> getCollectionByClass(Class<E> expected, String key) {
		Collection<E> res = new ArrayList<>();

		if (contains(key))
			if (get(key) instanceof JSONArray)
				((JSONArray) get(key)).forEach(a -> {
					try {
						E casted = expected.cast(a);
						res.add(casted);
					} catch (ClassCastException ex) {
						Bot.currentBot.getLogger().severe("Cannot cast object to " + expected.getSimpleName());
					}
				});
		return res;
	}

	public boolean contains(String key) {
		return this.get(key) != null;
	}

	public JSONObject toJsonObject() {
		return configuration;
	}

	public Set<String> keySet() {
		return this.configuration.keySet();
	}

	public ConfigurationSection getSection(String key) {
		return getSection(key, configuration);
	}

	private ConfigurationSection getSection(String key, JSONObject parent) {
		ConfigurationSection res;
		if (!contains(key))
			return null;
		if (!(obt(key, parent) instanceof JSONObject))
			return null;
		else {
			res = new ConfigurationSection() {

				private JSONObject section = (JSONObject) obt(key, parent);

				@Override
				public ConfigurationSection getSubSection(String key) {
					return getSection(key, section);
				}

				@Override
				public short getShort(String key) {
					try {
						return (short) this.get(key);
					} catch (ClassCastException e) {
						Bot.currentBot.getLogger().severe("Cannot cast object to short!");
						return 0;
					}
				}

				@Override
				public long getLong(String key) {
					try {
						return (long) this.get(key);
					} catch (ClassCastException e) {
						Bot.currentBot.getLogger().severe("Cannot cast object to long!");
						return 0;
					}
				}

				@Override
				public Collection<Object> getCollection(String key) {
					Collection<Object> res = new ArrayList<>();

					if (this.contains(key)) {
						if (this.get(key) instanceof JSONArray) {
							((JSONArray) get(key)).forEach(res::add);
							return res;
						} else
							return null;
					} else {
						return null;
					}
				}

				@Override
				public int getInt(String key) {
					try {
						return (int) this.get(key);
					} catch (ClassCastException e) {
						Bot.currentBot.getLogger().severe("Cannot cast object to int!");
						return 0;
					}
				}

				@Override
				public float getFloat(String key) {
					try {
						return (float) this.get(key);
					} catch (ClassCastException e) {
						Bot.currentBot.getLogger().severe("Cannot cast object to float!");
						return 0;
					}
				}

				@Override
				public double getDouble(String key) {
					try {
						return (double) this.get(key);
					} catch (ClassCastException e) {
						Bot.currentBot.getLogger().severe("Cannot cast object to double!");
						return 0;
					}
				}

				@Override
				public <E> E get(Class<E> expected, String key) {
					try {
						return expected.cast(this.get(key));
					} catch (ClassCastException e) {
						Bot.currentBot.getLogger().severe("Cannot cast object to " + expected.getSimpleName() + "!");
						return null;
					}
				}

				@Override
				public Object get(String key) {
					return obt(key, section);
				}

				@Override
				public String getString(String key) {
					try {
						return (String) this.get(key);
					} catch (ClassCastException e) {
						Bot.currentBot.getLogger().severe("Cannot cast object to string!");
						return null;
					}
				}

				@Override
				public byte getByte(String key) {
					try {
						return (byte) this.get(key);
					} catch (ClassCastException e) {
						Bot.currentBot.getLogger().severe("Cannot cast object to byte!");
						return 0;
					}
				}

				@Override
				public Set<String> keySet() {
					return section.keySet();
				}

				@Override
				public JSONObject toJsonObject() {
					return section;
				}

				@Override
				public boolean contains(String key) {
					return this.get(key) != null;
				}
			};
		}
		return res;
	}

	private void put(String key, Object value, JSONObject parent) {
		if (key.contains(".")) {
			String[] names = key.split("\\.");
			StringBuilder builder = new StringBuilder();
			String parenthesis = "";
			for (int i = 1; i < names.length; i++) {
				builder.append("{\"" + names[i] + "\": ");
				parenthesis += "}";
			}
			if (value instanceof ISerializable)
				builder.append(((ISerializable) value).serialize());
			else
				builder.append(JSONObject.valueToString(value));
			builder.append(parenthesis);

			JSONObject toPut = new JSONObject(builder.toString());

			parent.put(names[0], toPut);
		} else {
			parent.put(key, value);
		}
	}
	
	public void set(String key, Object value) {
		put(key, value, configuration);
	}

	public void save() {
		FileWriter writer = null;
		try {
			writer = new FileWriter(configFile);
			writer.write(JSONPrettyPrinter.prettyPrint(configuration));
		} catch (IOException e) {
			Bot.currentBot.getLogger().severe("Cannot save config! Exception occurred!");
			e.printStackTrace();
		} finally {
			try {
				writer.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}

}
