package org.harvey.respiratory.server.service;

import com.baomidou.mybatisplus.core.toolkit.support.SFunction;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.List;
import java.util.stream.Collectors;

/**
 * TODO
 *
 * @author <a href="mailto:harvey.blocks@outlook.com">Harvey Blocks</a>
 * @version 1.0
 * @date 2025-05-22 22:08
 */
public class ServiceUtil {
    private ServiceUtil(){}
    public static <T> List<Integer> selectIntegerIds(IService<T> service, SFunction<T, Integer> mapper) {
        return selectUnique(service,mapper);
    }

    public static <T> List<Long> selectLongIds(IService<T> service, SFunction<T, Long> mapper) {
        return selectUnique(service,mapper);
    }
    public static <E,R> List<R> selectUnique(IService<E> service, SFunction<E, R> mapper) {
        return service.lambdaQuery().select(mapper).list().stream().map(mapper).distinct().collect(Collectors.toList());
    }

}
