/**
 *  Copyright (c) 2016,xiaolan_it. All rights reserved.
 */
package com.xiaolan.generate;

/**
 * 
 * 代码自动生成工具类<br>
 * 支持（<br>
 * 1、vo 基础数据对象（com.xxx.xxx.vo.xx.XxxVO）<br>
 * 2、dao <br>
 * 3、service<br>
 * 4、action<br>
 * ）
 * 
 * @author wangshiyan
 * @date 2016年10月28日 下午3:58:45
 */
public class GenerateUtil {
	/**
	 * 生成VO对象
	 * 
	 * @param generate
	 *            参数对象
	 * @return 代码内容
	 */
	public static String getContentVO(Generate generate) {
		String path = "";
		String moduleName = generate.getModuleName();// 模块名
		String superClass = generate.getClassVOSuperClass();// extends父类
		StringBuffer stringBuffer = new StringBuffer();
		// 1、package
		stringBuffer.append("package " + GenerateConstant.PACKAGE_HOST + "vo"
				+ (null != moduleName ? "." + moduleName : "") + ";\n\n");
		// 2、import package
		if (null != generate.getImportPackages()) {
			for (String packageStr : generate.getImportPackages()) {
				stringBuffer.append("import " + packageStr + ";\n");
			}
		}
		stringBuffer.append("\n");
		// 3、类注释
		stringBuffer.append("/**\n *" + generate.getClassComments() + "_VO\n */\n");
		// 4、创建类
		stringBuffer.append("public class " + generate.getClassName() + "VO "
				+ (null != superClass ? "extends " + superClass : "") + " {\n");

		// 5、类属性
		String[] fields = generate.getFields();
		StringBuffer fieldBuffer = new StringBuffer();// 属性
		StringBuffer fieldSetGetVoidBuffer = new StringBuffer();// 属性set/get方法
		String field = null;
		String fieldStartUpper = null;// 属性首字母大写
		for (int i = 0, length = fields.length; i < length; i++) {
			field = fields[i];
			fieldStartUpper = field.substring(0, 1).toUpperCase() + field.substring(1);
			fieldBuffer.append("    /**\n    * " + generate.getFieldComments()[i] + "\n    */\n");// 属性注释
			fieldBuffer.append("    private " + generate.getFieldTypes()[i] + " " + field + ";\n\n");// 属性
			// set方法
			fieldSetGetVoidBuffer.append("    public void set" + fieldStartUpper + "(" + generate.getFieldTypes()[i]
					+ " " + field + ") {\n");
			fieldSetGetVoidBuffer.append("        this." + field + " = " + field + ";\n    }\n\n");
			// get方法
			fieldSetGetVoidBuffer
					.append("    public " + generate.getFieldTypes()[i] + " get" + fieldStartUpper + "() {\n");
			fieldSetGetVoidBuffer.append("        return " + field + ";\n    }\n");
		}
		stringBuffer.append(fieldBuffer);
		stringBuffer.append(fieldSetGetVoidBuffer);

		stringBuffer.append("}\n");
		return stringBuffer.toString();
	}

	public static void main(String[] args) {
		Generate generate = new Generate();
		generate.setClassComments("用户信息");
		generate.setClassName("User");
		generate.setFieldComments(new String[] { "id", "名称", "备注" });
		generate.setFields(new String[] { "id", "name", "desc" });
		generate.setFieldTypes(new String[] { "int", "String", "String" });
		System.out.println(GenerateUtil.getContentVO(generate));
	}
}
