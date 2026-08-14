math.randomseed(os.time())

local NUM_URLS = 1000000

request = function()
    local id = math.random(1, NUM_URLS)
    local short_code = string.format("test%06d", id)

    return wrk.format("GET", "/" .. short_code)
end