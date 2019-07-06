//package com.readwriteseparate.sharding;
//
//import com.dangdang.ddframe.rdb.sharding.api.ShardingValue;
//import com.dangdang.ddframe.rdb.sharding.api.strategy.table.SingleKeyTableShardingAlgorithm;
//import com.google.common.collect.Range;
//
//import java.util.Collection;
//import java.util.LinkedHashSet;
//
//public class ModuloTableShardingAlgorithm implements SingleKeyTableShardingAlgorithm<Integer> {
//
//    /**
//     * 对于分片字段的等值操作 都走这个方法。(包括 插入 更新)
//     * 如：
//     * <p>
//     * select * from t_order from t_order where order_id = 11
//     * └── SELECT *  FROM t_order_1 WHERE order_id = 11
//     * select * from t_order from t_order where order_id = 44
//     * └── SELECT *  FROM t_order_0 WHERE order_id = 44
//     * </P>
//     */
//	@Override
//    public String doEqualSharding(final Collection<String> tableNames, final ShardingValue<Integer> shardingValue) {
//        for (String each : tableNames) {
//            if (each.endsWith(shardingValue.getValue() % 2 + "")) {
//                return each;
//            }
//        }
//        throw new IllegalArgumentException();
//    }
//
//    /**
//     * 对于分片字段的in操作，都走这个方法。
//    *  select * from t_order from t_order where order_id in (11,44)
//    *          ├── SELECT *  FROM t_order_0 WHERE order_id IN (11,44)
//    *          └── SELECT *  FROM t_order_1 WHERE order_id IN (11,44)
//    *  select * from t_order from t_order where order_id in (11,13,15)
//    *          └── SELECT *  FROM t_order_1 WHERE order_id IN (11,13,15)
//    *  select * from t_order from t_order where order_id in (22,24,26)
//    *          └──SELECT *  FROM t_order_0 WHERE order_id IN (22,24,26)
//    */
//	@Override
//    public Collection<String> doInSharding(final Collection<String> tableNames, final ShardingValue<Integer> shardingValue) {
//        Collection<String> result = new LinkedHashSet<>(tableNames.size());
//        for (Integer value : shardingValue.getValues()) {
//            for (String tableName : tableNames) {
//                if (tableName.endsWith(value % 2 + "")) {
//                    result.add(tableName);
//                }
//            }
//        }
//        return result;
//    }
//
//    /**
//     * 对于分片字段的between操作都走这个方法。
//    *  select * from t_order from t_order where order_id between 10 and 20
//    *          ├── SELECT *  FROM t_order_0 WHERE order_id BETWEEN 10 AND 20
//    *          └── SELECT *  FROM t_order_1 WHERE order_id BETWEEN 10 AND 20
//    */
//	@Override
//    public Collection<String> doBetweenSharding(final Collection<String> tableNames, final ShardingValue<Integer> shardingValue) {
//        Collection<String> result = new LinkedHashSet<>(tableNames.size());
//        Range<Integer> range = (Range<Integer>) shardingValue.getValueRange();
//        for (Integer i = range.lowerEndpoint(); i <= range.upperEndpoint(); i++) {
//            for (String each : tableNames) {
//                if (each.endsWith(i % 2 + "")) {
//                    result.add(each);
//                }
//            }
//        }
//        return result;
//    }
//
//}
