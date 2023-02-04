# PaidPortals

Experience a new level of community cooperation with this Minecraft plugin,
where players must pool their resources using Vault economy to unlock access
to the Nether and End dimensions.

Note: if you're upgrading from 1.0 to 1.1, please make sure to reset your `lang.yml`!

## Table of Contents

- [Versions](#ver)
- [Setup](#setup)
- [Config](#conf)
- [Commands](#cmd)
- [Permissions](#perms)
- [Placeholders](#placeholders)
- [Dependencies](#depend)
- [Guides](#guide)
  - [Database](#db)
- [How to contact me](#contact)

### <a name="ver">Versions</a>

As of v1.1, this plugin was written for Spigot 1.19.3 \
I'm planning to add multiversion support in the future

### <a name="setup">Setup</a>

1. Download the latest version
2. Drag and drop the `.jar` into your `/plugins` folder
3. Make sure to also install the [Dependencies](#depend)
4. Start your server and enjoy!

### <a name="conf">Config</a>

```yaml
# METRICS (bStats)
metrics: true

# DATABASE
# types: mysql, mongodb, yaml
# default ports: 3306 (mysql), 27017 (mongodb)
database:
  type: yaml

  # (none of the following database settings apply to yaml)
  host: localhost
  port: 3306

  name: paidportals
  username: portal_user
  password:

# ECONOMY POOL SETTINGS
# unlock-amount: total amount the players need to pay the pool to unlock a dimension
nether:
  enable: true
  unlock-amount: 5000

the_end:
  enable: true
  unlock-amount: 20000

# PORTAL SETTINGS
# these settings apply before the dimension goal is reached
player-portal-create: false
player-portal-enter: false
```

For information on database setup, please see the [Database Guide](#db)

### <a name="cmd">Commands</a>

| Command     | Arguments | Alias                       | Description                                            |
|-------------|-----------|-----------------------------|--------------------------------------------------------|
| /reload     | -         | -                           | Reload the config                                      |
| /resetpool  | -         | -                           | Reset the money pool                                   |
| /viewpool   | -         | -                           | View the money pool                                    |
| /dpay       | [amount]  | dimensionpool, dimensionpay | Pay a certain amount of money into the dimensions pool |

### <a name="perms">Permissions</a>

| Permission               | Default  | Description                                                                 |
|--------------------------|----------|-----------------------------------------------------------------------------|
| paidportals.reload       | OP       | Permission to use `/reload`                                                 |
| paidportals.resetpool    | OP       | Permission to use `/resetpool`                                              |
| paidportals.ignore       | OP       | Allows players to use portals, even when its still locked for everyone else |
| paidportals.viewpool     | Everyone | Permission to use `/viewpool`                                               |
| paidportals.dimensionpay | Everyone | Permission to use `/dpay`                                                   |

### <a name="placeholders">Placeholders</a>

| Placeholder           | Description                           |
|-----------------------|---------------------------------------|
| %paidportals_nether%  | Returns the lock status of the Nether |
| %paidportals_the_end% | Returns the lock status of the End    |

### <a name="depends">Dependencies</a>

1. [Vault](https://www.spigotmc.org/resources/vault.34315/)
2. Any Vault supported Economy plugin (e.g. [EssentialsX](https://www.spigotmc.org/resources/essentialsx.9089/))
3. [PlaceholderAPI](https://www.spigotmc.org/resources/placeholderapi.6245/) (optional)

### <a name="guide">Guides</a>

#### <a name="db">Database</a>

1. MySQL 
   - Make sure to install MySQL
   - open the mysql shell
   - `CREATE USER 'portal_user'@'localhost' IDENTIFIED BY 'GENERATED_PASSWORD_HERE'`
   - `CREATE DATABASE IF NOT EXISTS paidportals`
   - `GRANT ALL PRIVILEGES ON paidportals.* TO 'portal_user'@'localhost' IDENTIFIED BY 'GENERATED_PASSWORD_HERE' WITH GRANT OPTION`


2. MongoDB
   - Make sure to install MongoDB
   - open the mongo shell
   - `use paidportals`
   - `db.createUser({ user: "portal_user", pwd: "GENERATED_PASSWORD_HERE", roles:" ["readWrite""] })`


1. YAML
   - No additional setup needed!


As of v1.0, this plugin only supports these 3 Database types
I'm planning to add SQLite in the future

### <a name="contact">How to contact me</a>

- [GitHub](https://github.com/maurxce/PaidPortals/issues)
- [Discord](https://maurxce.dev/discord)
- [Spigot](https://www.spigotmc.org/members/2lewd4u.1670754/)
