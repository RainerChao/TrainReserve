package com.jflyfox.order;

import com.jfinal.plugin.activerecord.Page;
import com.jflyfox.component.base.BaseProjectController;
import com.jflyfox.jfinal.component.annotation.ControllerBind;
import com.jflyfox.jfinal.component.db.SQLUtils;
import com.jflyfox.system.department.DepartmentSvc;
import com.jflyfox.util.StrUtils;

@ControllerBind(controllerKey = "/admin/order")
public class OrderController extends BaseProjectController {
	private static final String path = "/pages/admin/order/order_";

	public void list() {
		Order model = getModelByAttr(Order.class);

		SQLUtils sql = new SQLUtils(" FROM "
				+ "t_order o," + "t_order_detail od," + "t_order_payment op," + "sys_user u " + " WHERE"
				+ " od.id = o.order_detail" + " AND op.id = o.order_payment" + " AND u.userid = o.order_user ");

		if (model.getAttrValues().length != 0) {
			sql.whereLike("username", model.getStr("username"));
			sql.whereLike("realname", model.getStr("realname"));
			sql.whereEquals("usertype", model.getInt("usertype"));
			sql.whereEquals("departid", model.getInt("departid"));
		}

		// 排序
		String orderBy = getBaseForm().getOrderBy();
		if (StrUtils.isEmpty(orderBy)) {
			sql.append(" order by userid desc");
		} else {
			sql.append(" order by ").append(orderBy);
		}

		Page<Order> page = Order.dao.paginate(getPaginator(), "SELECT o.id,u.realname,od.license_level,od.training_field,"
				+ "od.training_start_time,od.duration,o.order_amount,op.pay_status",
				sql.toString().toString());
		
		// 下拉框
		setAttr("departSelect", new DepartmentSvc().selectDepart(model.getInt("departid")));

		setAttr("page", page);
		setAttr("attr", model);
		render(path + "list.html");
	}
	public static void main(String[] args) {
		System.out.println(" SELECT " + "o.id," + "u.realname," + "od.license_level," + "od.training_field,"
				+ "od.training_start_time,od.duration,o.order_amount,op.pay_status FROM "
				+ "t_order o," + "t_order_detail od," + "t_order_payment op," + "sys_user u " + " WHERE"
				+ " od.id = o.order_detail" + " AND op.id = o.order_payment" + " AND u.userid = o.order_user ");
	}
}
