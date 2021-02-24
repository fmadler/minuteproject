use buslogic_intro;

set FOREIGN_KEY_CHECKS = 0;

delete from `buslogic_intro.customer`;

delete from `product`;

delete from `payment`;

delete from `Product_Billofmaterials`;

delete from `payment_purchaseorder_allocation`;
	
delete from `employee`;

delete from `employee_audit`;

delete from `employee_raise_service`;

delete from `department`;
	
delete from `purchaseorder`;
	
delete from `lineitem`;

delete from `lineitem_note`;
	
delete from `lineitem_usage`;
	
delete from `valid_customerlevel`;


	
	