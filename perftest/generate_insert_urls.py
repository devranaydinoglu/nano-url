NUM_URLS = 1000000

with open("insert_urls.cql", "w") as f:
    f.write("USE spring_cassandra;\n\n")

    for i in range(1, NUM_URLS + 1):
        short_code = f"test{i:06d}"

        f.write(
            f"INSERT INTO \"Url\" "
            f"(short_code, original_url, snowflake_id, usage_counter, expires_at, created_at) "
            f"VALUES "
            f"('{short_code}', "
            f"'https://example.com/page/{i}', "
            f"{i}, "
            f"0, "
            f"4102444800000, "
            f"1735689600000);\n"
        )