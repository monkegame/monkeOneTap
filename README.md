# monkebotplugin
minecraft plugin for the [urlmonkebot]("https://github.com/mrsherobrine/monkebot")
requires you have an sqlite database as i'm too lazy to implement mysql (get ez gged on)

# dependencies
* papermc api
* gradle 
* jdk >= 15.0.2
* sqlite
* a little bit of masochism

# how to use
in the ``config.yml``, set your dblocation like: D://databases/database.db

the dbupdaterate is in seconds, default is 10 minutes

refer to [urlthe docs](https://papermc.io/javadocs/paper/1.16/org/bukkit/Material.html) for what item you can use

name is whatever you want it to be

# how to setup your databasee
make 3 columns: uuid (text, primary key, unique), username (text), killcount (integer)
