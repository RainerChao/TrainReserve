package com.jflyfox.order;

import com.jflyfox.component.base.BaseProjectModel;
import com.jflyfox.jfinal.component.annotation.ModelBind;

@ModelBind(table = "t_order", key = "id")
public class Order extends BaseProjectModel<Order>{
	public static final Order dao = new Order();
	
}
