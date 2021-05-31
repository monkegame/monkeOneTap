# monkebotplugin
minecraft plugin for the [monkebot]("https://github.com/mrsherobrine/monkebot")

requires you have an sqlite database as i'm too lazy to implement mysql (get ez gged on)

# dependencies
* jdk >= 15.0.2
* sqlite
* a little bit of masochism
* a database viewer (optional but handy)

# how to use
in the ``config.yml``, set your dblocation like: D://databases/database.db

the dbupdaterate is in seconds, default is 10 minutes

refer to [the docs](https://papermc.io/javadocs/paper/1.16/org/bukkit/Material.html) for what item you can use

name is whatever you want it to be

# how to setup

make a database table with 3 columns: uuid (text, primary key, unique), username (text), killcount (integer) 

specify your database location and table in the config.yml
