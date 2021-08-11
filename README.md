# monkeOneTap
minecraft plugin for the [monkegame server](https://monkegame.online)

this plugin is made to our needs so we won't be adding new features unless we *really* need to, nor will we actively provide support. (you're on your own, basically)

# requirements
* java >= 16
* sqlite database
* a little bit of masochism
* a database viewer (optional but handy)
* a paper server

# how to use
in the ``config.yml``, set your dblocation like: D://databases/database.db (linux users: you should know how to do it.)

in your database, make a table with 3 columns: uuid (text, primary key, unique), username (text), killcount (integer) and in ``config.yml`` point to the table's name

the dbupdaterate is in seconds, default is 10 minutes

refer to [the docs](https://papermc.io/javadocs/paper/1.16/org/bukkit/Material.html) for what item you can use

name is whatever you want it to be

# how to setup

specify your database location and table in the config.yml. if it spams your console with sqlite errors, you did it wrong.
