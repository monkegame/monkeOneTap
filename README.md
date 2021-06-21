# monkeOneTap
minecraft plugin for the [monkegame server]("https://monkegame.online")

requires you have an sqlite database (for now) as i'm too lazy to implement mysql (get ez gged on)

# dependencies
* java >= 15.0.2
* sqlite database
* a little bit of masochism
* a database viewer (optional but handy)
* a papermc server (no i won't port it to spigot, paper has so much more API)

# how to use
in the ``config.yml``, set your dblocation like: D://databases/database.db

in your database, make a table with 3 columns: uuid (text, primary key, unique), username (text), killcount (integer) and in ``config.yml`` point to the table's name

the dbupdaterate is in seconds, default is 10 minutes

refer to [the docs](https://papermc.io/javadocs/paper/1.16/org/bukkit/Material.html) for what item you can use

name is whatever you want it to be

# how to setup

specify your database location and table in the config.yml


# todo
[] autocreate database on first run
[] add a table to said database to log last access
