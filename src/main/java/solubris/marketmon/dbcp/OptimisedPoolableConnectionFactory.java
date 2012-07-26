package solubris.marketmon.dbcp;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Collection;

import org.apache.commons.dbcp.AbandonedConfig;
import org.apache.commons.dbcp.ConnectionFactory;
import org.apache.commons.dbcp.PoolableConnectionFactory;
import org.apache.commons.pool.KeyedObjectPoolFactory;
import org.apache.commons.pool.ObjectPool;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Override {@link PoolableConnectionFactory} to use a preparedStatement in the validateConnection method
 *
 * @author Tim Walters
 * @version $Revision: $ $Date: $
 */
public class OptimisedPoolableConnectionFactory extends PoolableConnectionFactory {
	static private final Logger logger = LoggerFactory.getLogger(OptimisedPoolableConnectionFactory.class);

    public OptimisedPoolableConnectionFactory(ConnectionFactory connFactory,
			ObjectPool<?> pool, KeyedObjectPoolFactory<?, ?> stmtPoolFactory,
			String validationQuery, boolean defaultReadOnly,
			boolean defaultAutoCommit, AbandonedConfig config) {
		super(connFactory, pool, stmtPoolFactory, validationQuery, defaultReadOnly,
				defaultAutoCommit, config);
	}

	public OptimisedPoolableConnectionFactory(ConnectionFactory connFactory,
			ObjectPool<?> pool, KeyedObjectPoolFactory<?, ?> stmtPoolFactory,
			String validationQuery, boolean defaultReadOnly,
			boolean defaultAutoCommit, int defaultTransactionIsolation,
			AbandonedConfig config) {
		super(connFactory, pool, stmtPoolFactory, validationQuery, defaultReadOnly,
				defaultAutoCommit, defaultTransactionIsolation, config);
	}

	public OptimisedPoolableConnectionFactory(ConnectionFactory connFactory,
			ObjectPool<?> pool, KeyedObjectPoolFactory<?, ?> stmtPoolFactory,
			String validationQuery, boolean defaultReadOnly,
			boolean defaultAutoCommit, int defaultTransactionIsolation,
			String defaultCatalog, AbandonedConfig config) {
		super(connFactory, pool, stmtPoolFactory, validationQuery, defaultReadOnly,
				defaultAutoCommit, defaultTransactionIsolation, defaultCatalog, config);
	}

	public OptimisedPoolableConnectionFactory(ConnectionFactory connFactory,
			ObjectPool<?> pool, KeyedObjectPoolFactory<?, ?> stmtPoolFactory,
			String validationQuery, Boolean defaultReadOnly,
			boolean defaultAutoCommit, int defaultTransactionIsolation,
			String defaultCatalog, AbandonedConfig config) {
		super(connFactory, pool, stmtPoolFactory, validationQuery, defaultReadOnly,
				defaultAutoCommit, defaultTransactionIsolation, defaultCatalog, config);
	}

	public OptimisedPoolableConnectionFactory(ConnectionFactory connFactory,
			ObjectPool<?> pool, KeyedObjectPoolFactory<?, ?> stmtPoolFactory,
			String validationQuery, boolean defaultReadOnly,
			boolean defaultAutoCommit, int defaultTransactionIsolation) {
		super(connFactory, pool, stmtPoolFactory, validationQuery, defaultReadOnly,
				defaultAutoCommit, defaultTransactionIsolation);
	}

	public OptimisedPoolableConnectionFactory(ConnectionFactory connFactory,
			ObjectPool<?> pool, KeyedObjectPoolFactory<?, ?> stmtPoolFactory,
			String validationQuery, Collection<?> connectionInitSqls,
			Boolean defaultReadOnly, boolean defaultAutoCommit,
			int defaultTransactionIsolation, String defaultCatalog,
			AbandonedConfig config) {
		super(connFactory, pool, stmtPoolFactory, validationQuery, connectionInitSqls,
				defaultReadOnly, defaultAutoCommit, defaultTransactionIsolation,
				defaultCatalog, config);
	}

	public OptimisedPoolableConnectionFactory(ConnectionFactory connFactory,
			ObjectPool<?> pool, KeyedObjectPoolFactory<?, ?> stmtPoolFactory,
			String validationQuery, Collection<?> connectionInitSqls,
			boolean defaultReadOnly, boolean defaultAutoCommit,
			int defaultTransactionIsolation) {
		super(connFactory, pool, stmtPoolFactory, validationQuery, connectionInitSqls,
				defaultReadOnly, defaultAutoCommit, defaultTransactionIsolation);
	}

	public OptimisedPoolableConnectionFactory(ConnectionFactory connFactory,
			ObjectPool<?> pool, KeyedObjectPoolFactory<?, ?> stmtPoolFactory,
			String validationQuery, Collection<?> connectionInitSqls,
			boolean defaultReadOnly, boolean defaultAutoCommit) {
		super(connFactory, pool, stmtPoolFactory, validationQuery, connectionInitSqls,
				defaultReadOnly, defaultAutoCommit);
	}

	public OptimisedPoolableConnectionFactory(ConnectionFactory connFactory,
			ObjectPool<?> pool, KeyedObjectPoolFactory<?, ?> stmtPoolFactory,
			String validationQuery, int validationQueryTimeout,
			Boolean defaultReadOnly, boolean defaultAutoCommit,
			int defaultTransactionIsolation, String defaultCatalog,
			AbandonedConfig config) {
		super(connFactory, pool, stmtPoolFactory, validationQuery,
				validationQueryTimeout, defaultReadOnly, defaultAutoCommit,
				defaultTransactionIsolation, defaultCatalog, config);
	}

	public OptimisedPoolableConnectionFactory(ConnectionFactory connFactory,
			ObjectPool<?> pool, KeyedObjectPoolFactory<?, ?> stmtPoolFactory,
			String validationQuery, int validationQueryTimeout,
			boolean defaultReadOnly, boolean defaultAutoCommit,
			int defaultTransactionIsolation) {
		super(connFactory, pool, stmtPoolFactory, validationQuery,
				validationQueryTimeout, defaultReadOnly, defaultAutoCommit,
				defaultTransactionIsolation);
	}

	public OptimisedPoolableConnectionFactory(ConnectionFactory connFactory,
			ObjectPool<?> pool, KeyedObjectPoolFactory<?, ?> stmtPoolFactory,
			String validationQuery, int validationQueryTimeout,
			boolean defaultReadOnly, boolean defaultAutoCommit) {
		super(connFactory, pool, stmtPoolFactory, validationQuery,
				validationQueryTimeout, defaultReadOnly, defaultAutoCommit);
	}

	public OptimisedPoolableConnectionFactory(ConnectionFactory connFactory,
			ObjectPool<?> pool, KeyedObjectPoolFactory<?, ?> stmtPoolFactory,
			String validationQuery, int validationQueryTimeout,
			Collection<?> connectionInitSqls, Boolean defaultReadOnly,
			boolean defaultAutoCommit, int defaultTransactionIsolation,
			String defaultCatalog, AbandonedConfig config) {
		super(connFactory, pool, stmtPoolFactory, validationQuery,
				validationQueryTimeout, connectionInitSqls, defaultReadOnly,
				defaultAutoCommit, defaultTransactionIsolation, defaultCatalog, config);
	}

	public OptimisedPoolableConnectionFactory(ConnectionFactory connFactory,
			ObjectPool<?> pool, KeyedObjectPoolFactory<?, ?> stmtPoolFactory,
			String validationQuery, int validationQueryTimeout,
			Collection<?> connectionInitSqls, boolean defaultReadOnly,
			boolean defaultAutoCommit, int defaultTransactionIsolation) {
		super(connFactory, pool, stmtPoolFactory, validationQuery,
				validationQueryTimeout, connectionInitSqls, defaultReadOnly,
				defaultAutoCommit, defaultTransactionIsolation);
	}

	public OptimisedPoolableConnectionFactory(ConnectionFactory connFactory,
			ObjectPool<?> pool, KeyedObjectPoolFactory<?, ?> stmtPoolFactory,
			String validationQuery, int validationQueryTimeout,
			Collection<?> connectionInitSqls, boolean defaultReadOnly,
			boolean defaultAutoCommit) {
		super(connFactory, pool, stmtPoolFactory, validationQuery,
				validationQueryTimeout, connectionInitSqls, defaultReadOnly,
				defaultAutoCommit);
	}

	public OptimisedPoolableConnectionFactory(ConnectionFactory connFactory,
			ObjectPool<?> pool, KeyedObjectPoolFactory<?, ?> stmtPoolFactory,
			String validationQuery, boolean defaultReadOnly,
			boolean defaultAutoCommit) {
		super(connFactory, pool, stmtPoolFactory, validationQuery, defaultReadOnly,
				defaultAutoCommit);
	}

	@Override
	public void validateConnection(Connection conn) throws SQLException {
        String query = _validationQuery;
        if(conn.isClosed()) {
            throw new SQLException("validateConnection: connection closed");
        }
        if(null != query) {
            PreparedStatement stmt = null;
            ResultSet rset = null;
            try {
            	logger.debug("preparing statement for validation query");
                stmt = conn.prepareStatement(query);
                if (_validationQueryTimeout > 0) {
                    stmt.setQueryTimeout(_validationQueryTimeout);
                }
                rset = stmt.executeQuery();
                if(!rset.next()) {
                    throw new SQLException("validationQuery didn't return a row");
                }
            } finally {
                if (rset != null) {
                    try {
                        rset.close();
                    } catch(Exception t) {
                        // ignored
                    }
                }
                if (stmt != null) {
                    try {
                        stmt.close();
                    } catch(Exception t) {
                        // ignored
                    }
                }
            }
        }
    }
}
