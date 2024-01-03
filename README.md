# Counter-Strike 2 personal game tracking statistics

Multiplatform application for tracking game stats for CS2, kills, deaths, damage and etc.

By default, it reads files from "matches/" folder containing .json files feel free to change the source for receiving the data

## JSON Structure
```JSON
{
        "server_name" : "Valve Counter-Strike 2 helsinki server",
        "map": "de_mirage",
        "total_rounds_played": "22",
        "players": 
                [
            {
                "team_name": "TERRORIST",
                "rounds_won": "13",
                "steam_id": "76561198202086716",
                "name": "pencilneck",
                "rank": "12860",
                "kills": "14",
                "deaths": "11",
                "assists": "10",
                "headshot_kills": "8",
                "damage": "2054",
                "utility_damage": "197",
                "enemies_flashed": "6",
                "mvps": "2",
                "ping": "19",
                "aces": "0",
                "four_k_rounds": "0",
                "three_k_rounds": "1"
            },
            {...}
                ]
}
```

## Where to get this data?
First option is to use third party applications for parsing demo files into `.json` data. My go-to library is written in `rust` and can be used in Python, JavaScript and WebAssembly (`demoparser2`). Link: https://github.com/LaihoE/demoparser

In repository, I have provided sample python script using the library `demoparser2` to automatically downloads the demo, unzips it, parses the necessary data and saves it to `.json` file.

*usage:*
```terminal
python parseDemo.py <link-to-demo.dem.bz2>
```

it will output to `match_data.json` incrementing the file name if it already exists.

## Supported platforms

-PC (Windows, Linux)

-Android

*support for other platforms will come soon*
