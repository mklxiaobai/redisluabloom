-- 字符串hash后的二进制数组字符串
local hash = ARGV[1]
-- 布隆key
local bloomkey =  KEYS[1]

if (redis.call('EXISTS',bloomkey)==0)
then
	return 'this bloomkey is null'
else
	for i=1,string.len(hash) do
	-- 如果传入二进制数组中的1与原先位图不符，则必然不存在
		if(string.sub(hash,i,i)=="1") then
			if(redis.call('getbit',bloomkey,i)=='0') then
			return 'this object does not exist'
			end
	end
	return 'this object exist'
end