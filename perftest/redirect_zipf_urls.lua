local NUM_URLS = 1000000
local ZIPF_S = 0.9

local cumulative = {}
local total = 0

-- Build cumulative distribution
for i = 1, NUM_URLS do
    total = total + (1 / (i ^ ZIPF_S))
    cumulative[i] = total
end

-- Normalize
for i = 1, NUM_URLS do
    cumulative[i] = cumulative[i] / total
end

local function zipf()
    local r = math.random()

    local low = 1
    local high = NUM_URLS

    while low < high do
        local mid = math.floor((low + high) / 2)

        if cumulative[mid] >= r then
            high = mid
        else
            low = mid + 1
        end
    end

    return low
end

request = function()
    local id = zipf()
    local short_code = string.format("test%06d", id)

    return wrk.format("GET", "/" .. short_code)
end