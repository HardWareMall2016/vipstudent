package com.vip.student.db;


import com.vip.student.base.App;

import org.aisen.orm.SqliteUtility;
import org.aisen.orm.SqliteUtilityBuilder;

public class OrmDBHelper {

	public static SqliteUtility getCacheSqlite() {
        final String db_name="cache_db";
        if (SqliteUtility.getInstance(db_name) == null)
            new SqliteUtilityBuilder().configDBName(db_name).configVersion(1).build(App.ctx);

		return SqliteUtility.getInstance(db_name);
	}

}
