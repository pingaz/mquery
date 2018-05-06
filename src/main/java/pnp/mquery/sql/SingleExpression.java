/**
 * 
 */
package pnp.mquery.sql;

import java.util.List;

import pnp.mquery.srv.Condition;
import pnp.mquery.srv.SingleCondition;

/**
 * @author ping
 *
 */
public class SingleExpression implements Expression {
	
	private final SingleCondition<?> condition;

	/**
	 * @param condition
	 */
	public SingleExpression(Condition condition) {
		super();
		this.condition = (SingleCondition<?>) condition;
	}
	

	/* (non-Javadoc)
	 * @see pnp.mquery.sql.Expression#addValue(java.util.List)
	 */
	@Override
	public void addValue(List<Object> values) {
		if(condition.getValue()!=null) {
			if(condition.getValue().getClass().isArray()) {
				for(Object v : (Object[])condition.getValue()) {
					values.add(v);
				}
			}else if(condition.getValue() instanceof List) {
				for(Object v : (List<?>)condition.getValue()) {
					values.add(v);
				}
			}else if(condition.getOper().equals(SingleCondition.OPERATION_LIKE)) {
				values.add("%"+condition.getValue()+"%");
			}else {
				values.add(condition.getValue());
			}
		}
	}


	/* (non-Javadoc)
	 * @see pnp.mquery.sql.Expression#appendSql(java.lang.String, java.lang.String, pnp.mquery.srv.Condition)
	 */
	@Override
	public StringBuffer appendSql(StringBuffer ql) {
		if(condition.getValue()!=null) {
			if(ql==null) {
				ql = new StringBuffer();
			}
			if(ql.length() != 0) {
				ql.append(" AND ");
			}
			return ql.append(condition.getKey()).append(transformCondition());
		}else {
			return ql;
		}
	}

	private String transformCondition() {
		switch (condition.getOper()) {
		case SingleCondition.OPERATION_BETWEEN:
			return " BETWEEN ? AND ?";
		case SingleCondition.OPERATION_EQUAL:
			return "= ?";
		case SingleCondition.OPERATION_GREAT:
			return "> ?";
		case SingleCondition.OPERATION_GREAT_EQUAL:
			return ">= ?";
		case SingleCondition.OPERATION_IN:
			return buildInExpression();
		case SingleCondition.OPERATION_LESS:
			return "< ?";
		case SingleCondition.OPERATION_LESS_EQUAL:
			return "<= ?";
		case SingleCondition.OPERATION_LIKE:
			return " like ?";
		case SingleCondition.OPERATION_NOT_EQUAL:
			return "!= ?";
		default:
			throw new IllegalArgumentException(condition.getOper());
		}
	}
	
	private String buildInExpression() {
		StringBuffer sb = new StringBuffer(" IN (");
		Object[] values = (Object[])condition.getValue();
		assert values.length != 0;
		for (int i = 0; i < values.length; i++) {
			sb.append("?,");
		}
		sb.deleteCharAt(sb.length()-1);
		sb.append(')');
		return sb.toString();
	}

}
