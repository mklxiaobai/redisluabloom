-- 字符串hash后的二进制数组字符串
local hash = ARGV[1]
-- 布隆key
local bloomkey =  KEYS[1]
-- 布隆集合大小
local size = redis.call('get',bloomkey+'size')

-- 如果此key不存在 初始化位图以及size
if (redis.call('EXISTS',bloomkey)==0)
then
	for i=1,string.len(hash) do
		redis.call('setbit',bloomkey,i,string.sub(hash,i,i))
		redis.call('set',bloomkey+'size','0')
	end
else
-- 将新二进制数组中的1覆盖原先位图的旧值?
	for i=1,string.len(hash) do
		if(string.sub(hash,i,i)=="1") then
			redis.call('setbit',bloomkey,i,'1')
			redis.call('INCR',bloomkey+'size')
	end
end