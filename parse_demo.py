from demoparser2 import DemoParser
import json
import os
import sys
import requests
from tqdm import tqdm
import bz2

url = sys.argv[1]
destination = "demos/demo.dem.bz2"

response = requests.get(url)

if response.status_code == 200:
    total_size = int(response.headers.get('content-length', 0))
    with open(destination, 'wb') as file, tqdm(
        desc='Downloading the demo...', total=total_size, unit='B', unit_scale=True
    ) as bar:
        for data in response.iter_content(chunk_size=1024):
            file.write(data)
            bar.update(len(data))

    print(f"Demo downloaded to: {destination}")
    compressed_demo = destination
    output = "demos/demo.dem"

    with open(output, 'wb') as decompressed_file:
        with bz2.BZ2File(compressed_demo, 'rb') as file:
            for data in iter(lambda: file.read(100*1024), b''):
                decompressed_file.write(data)

    print(f"Demo decompressed to: {output}")

    parser = DemoParser(output)
    max_tick = parser.parse_event("round_end")["tick"].max()

    wanted_fields = ["kills_total",
                     "deaths_total",
                     "mvps",
                     "damage_total",
                     "team_name",
                     "total_rounds_played",
                     "team_rounds_total",
                     "rank",
                     "ping",
                     "assists_total",
                     "headshot_kills_total",
                     "ace_rounds_total",
                     "4k_rounds_total",
                     "3k_rounds_total",
                     "utility_damage_total",
                     "enemies_flashed_total"]
    df = parser.parse_ticks(wanted_fields, ticks=[max_tick])
    # print(df)
    header = parser.parse_header()
    map_name = header["map_name"]
    players = df["name"]
    players_id = df["steamid"]
    total_rounds_played = df["total_rounds_played"]
    kills_total = df["kills_total"]
    mvps = df["mvps"]
    damage_total = df["damage_total"]
    deaths_total = df["deaths_total"]
    team_name = df["team_name"]
    team_rounds_played = df["team_rounds_total"]
    rank = df["rank"]
    ping = df["ping"]
    server_name = header["server_name"]
    demo_version_guid = header["demo_version_guid"]
    headshot_kills_total = df["headshot_kills_total"]
    assists_total = df["assists_total"]
    ace_rounds_total = df["ace_rounds_total"]
    four_k_rounds_total = df["4k_rounds_total"]
    three_k_rounds_total = df["3k_rounds_total"]
    utility_damage = df["utility_damage_total"]
    enemies_flashed = df["enemies_flashed_total"]

    json_data = {
        "server_name": str(server_name),
        "map": str(map_name),
        "total_rounds_played": str(total_rounds_played[0]),
        "players": []
    }

    player_num = 10

    for i in range(player_num):
        player_data = {
            "team_name": str(team_name[i]),
            "rounds_won": str(team_rounds_played[i]),
            "steam_id": str(players_id[i]),
            "name": str(players[i]),
            "rank": str(rank[i]),
            "kills": str(kills_total[i]),
            "deaths": str(deaths_total[i]),
            "assists": str(assists_total[i]),
            "headshot_kills": str(headshot_kills_total[i]),
            "damage": str(damage_total[i]),
            "utility_damage": str(utility_damage[i]),
            "enemies_flashed": str(enemies_flashed[i]),
            "mvps": str(mvps[i]),
            "ping": str(ping[i]),
            "aces": str(ace_rounds_total[i]),
            "four_k_rounds": str(four_k_rounds_total[i]),
            "three_k_rounds": str(three_k_rounds_total[i])
        }

        json_data["players"].append(player_data)


    def save_json_with_increment(json_data, filename='match_data.json'):
        count = 0
        while True:
            if count == 0:
                new_filename = filename
            else:
                file_parts = os.path.splitext(filename)
                new_filename = f"{file_parts[0]}_{count}{file_parts[1]}"

            if not os.path.exists(new_filename):
                with open(new_filename, 'w') as json_file:
                    json.dump(json_data, json_file, indent=4)
                    print(f"Data saved to {new_filename}")
                break
            count += 1


    #print(json_data)
    print("Demo is parsed")
    save_json_with_increment(json_data)
    os.remove(output)
    os.remove(destination)

else:
    print(f"Failed to download the demo. Error code: {response.status_code}")

