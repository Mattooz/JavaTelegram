# JavaTelegram
Telegram Java Api

Easy-to-use and powerful API.


## Usage

###Getting Started

Althought this api is in infancy, it is usable. I'm updating it daily, so don't miss any update. I upload, only the versions that are actually stable to use. So don't worry about if you're one version behind.
Here how you can start using this API. This API is 

```
public class MyBOT {

  public static Bot bot = new Bot("yourtoken");
  
  public static void main(String[] args) {
    try {
      bot.on();
    } catch (RequestErrorException e) {
      System.out.println(e.getError());
    }
    registerCommand();
    registerEvents();
  }
  
  private static void registerCommand() {
    Commands.registerCommandListener(new MyCommand());
  }
  
  private static void registerEvents() {
    EventManager.registerEvent(new MyEventListener());
  }
}
```

###Commands
Commands are pretty basic. When an user issues a message that is started with "/" a command is triggered.
You can easily do this with events also.

```
public class MyCommand implements CommandListener {


