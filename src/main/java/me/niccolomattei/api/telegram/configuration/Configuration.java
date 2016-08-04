package me.niccolomattei.api.telegram.configuration;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Set;

import org.json.JSONObject;

import me.niccolomattei.api.telegram.Bot;

public class Configuration {

	public static final String defaultPath = "bot/config/";
	public static final File defaultPathFile = new File("bot/config/");

	File configFile;
	JSONObject configuration;

	public static boolean generateDefault(String name) {
		File f = new File(defaultPathFile, name);
		return generateDefault(f);
	}

	public static boolean generateDefault(File path) {
		FileWriter writer = null;
		if (!path.exists()) {
			defaultPathFile.mkdirs();
			try {
				path.createNewFile();

				writer = new FileWriter(path);
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

	public Object get(String key) {
		return configuration.get(key);
	}

	public <E extends Object> E get(Class<E> expected, String key) {
		try {
			return expected.cast(configuration.get(key));
		} catch (ClassCastException e) {
			Bot.currentBot.getLogger().severe("Cannot cast object to " + expected.getSimpleName() + "!");
			return null;
		}
	}

	public String getString(String key) {
		try {
			return (String) configuration.get(key);
		} catch (ClassCastException e) {
			Bot.currentBot.getLogger().severe("Cannot cast object to string!");
			return null;
		}
	}

	public int getInt(String key) {
		try {
			return (int) configuration.get(key);
		} catch (ClassCastException e) {
			Bot.currentBot.getLogger().severe("Cannot cast object to int!");
			return 0;
		}
	}

	public double getDouble(String key) {
		try {
			return (double) configuration.get(key);
		} catch (ClassCastException e) {
			Bot.currentBot.getLogger().severe("Cannot cast object to double!");
			return 0;
		}
	}

	public long getLong(String key) {
		try {
			return (long) configuration.get(key);
		} catch (ClassCastException e) {
			Bot.currentBot.getLogger().severe("Cannot cast object to long!");
			return 0;
		}
	}

	public short getShort(String key) {
		try {
			return (short) configuration.get(key);
		} catch (ClassCastException e) {
			Bot.currentBot.getLogger().severe("Cannot cast object to short!");
			return 0;
		}
	}

	public float getFloat(String key) {
		try {
			return (float) configuration.get(key);
		} catch (ClassCastException e) {
			Bot.currentBot.getLogger().severe("Cannot cast object to float!");
			return 0;
		}
	}

	public byte getByte(String key) {
		try {
			return (byte) configuration.get(key);
		} catch (ClassCastException e) {
			Bot.currentBot.getLogger().severe("Cannot cast object to byte!");
			return 0;
		}
	}

	public Collection<Object> getCollection(String key) {
		Collection<Object> res = new ArrayList<>();

		if (configuration.has(key)) {
			configuration.getJSONArray(key).forEach(a -> res.add(a));

			return res;
		} else {
			return null;
		}
	}

	public boolean contains(String key) {
		return configuration.has(key);
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
		if (!parent.has(key))
			return null;
		if (!(parent.get(key) instanceof JSONObject))
			return null;
		else {
			res = new ConfigurationSection() {

				JSONObject section = parent.getJSONObject(key);

				@Override
				public ConfigurationSection getSubSection(String key) {
					return getSection(key, section);
				}

				@Override
				public short getShort(String key) {
					try {
						return (short) section.get(key);
					} catch (ClassCastException e) {
						Bot.currentBot.getLogger().severe("Cannot cast object to short!");
						return 0;
					}
				}

				@Override
				public long getLong(String key) {
					return 0;
				}

				@Override
				public Collection<Object> getList(String key) {
					Collection<Object> res = new ArrayList<>();

					if (section.has(key)) {
						section.getJSONArray(key).forEach(a -> res.add(a));

						return res;
					} else {
						return null;
					}
				}

				@Override
				public int getInt(String key) {
					try {
						return (int) section.get(key);
					} catch (ClassCastException e) {
						Bot.currentBot.getLogger().severe("Cannot cast object to int!");
						return 0;
					}
				}

				@Override
				public float getFloat(String key) {
					try {
						return (float) section.get(key);
					} catch (ClassCastException e) {
						Bot.currentBot.getLogger().severe("Cannot cast object to float!");
						return 0;
					}
				}

				@Override
				public double getDouble(String key) {
					try {
						return (double) configuration.get(key);
					} catch (ClassCastException e) {
						Bot.currentBot.getLogger().severe("Cannot cast object to double!");
						return 0;
					}
				}

				@Override
				public <E> E get(Class<E> expected, String key) {
					try {
						return expected.cast(section.get(key));
					} catch (ClassCastException e) {
						Bot.currentBot.getLogger().severe("Cannot cast object to " + expected.getSimpleName() + "!");
						return null;
					}
				}

				@Override
				public Object get(String key) {
					return section.get(key);
				}

				@Override
				public String getString(String key) {
					try {
						return (String) section.get(key);
					} catch (ClassCastException e) {
						Bot.currentBot.getLogger().severe("Cannot cast object to string!");
						return null;
					}
				}

				@Override
				public byte getByte(String key) {
					try {
						return (byte) configuration.get(key);
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
					return section.has(key);
				}
			};
		}
		return res;
	}

	public void put(String key, Object value) {
		configuration.put(key, value);
	}

	public void save() {
		FileWriter writer = null;
		try {
			writer = new FileWriter(configFile);
			writer.write(configuration.toString());
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
