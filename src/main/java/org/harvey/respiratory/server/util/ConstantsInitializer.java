package org.harvey.respiratory.server.util;


import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import org.harvey.respiratory.server.Constants;

import java.text.ParseException;
import java.util.Date;

/**
 * TODO
 *
 * @author <a href="mailto:harvey.blocks@outlook.com">Harvey Blocks</a>
 * @version 1.0
 * @date 2025-05-20 18:19
 */
public class ConstantsInitializer {
    public static <T> Page<T> initPage(Integer page, Integer limit) {
        if (page == null) {
            page = 1;
        }
        if (limit == null) {
            limit = Constants.DEFAULT_PAGE_SIZE;
        }
        return new Page<>(page, limit);
    }

    public static RangeDate initDateRange(String startDateString, String endDateString) throws ParseException {
        return new RangeDate(initDate(startDateString), initDate(endDateString));
    }

    public static Date initDate(String endDateString) throws ParseException {
        if (endDateString == null) {
            return null;
        } else {
            return Constants.DEFAULT_DATE_FORMAT.parse(endDateString);
        }
    }

    public static Date nowDateTime() {
        return new Date(System.currentTimeMillis());
    }
}
