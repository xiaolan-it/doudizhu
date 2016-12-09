/**
 *  Copyright (c) 2016,xiaolan_it. All rights reserved.
 */
package com.xiaolan.generate;

/**
 * 自动生成代码所需的对象
 *
 * @author wangshiyan
 * @date 2016年10月28日 下午4:14:09
 */
public class Generate {
	/**
	 * 需要导入的包
	 */
	private String[] importPackages;
	/**
	 * 功能模块名
	 */
	private String moduleName;
	/**
	 * VO类的继承父类
	 */
	private String classVOSuperClass;
	/**
	 * class类名
	 */
	private String className;
	/**
	 * 类注释
	 */
	private String classComments;

	/**
	 * 字段数组
	 */
	private String[] fields;

	/**
	 * 字段数据类型
	 */
	private String[] fieldTypes;
	/**
	 * 字段注释
	 */
	private String[] fieldComments;

	/**
	 * @return the classVOSuperClass
	 */
	public String getClassVOSuperClass() {
		return classVOSuperClass;
	}

	/**
	 * @param classVOSuperClass
	 *            the classVOSuperClass to set
	 */
	public void setClassVOSuperClass(String classVOSuperClass) {
		this.classVOSuperClass = classVOSuperClass;
	}

	/**
	 * @return the importPackages
	 */
	public String[] getImportPackages() {
		return importPackages;
	}

	/**
	 * @param importPackages
	 *            the importPackages to set
	 */
	public void setImportPackages(String[] importPackages) {
		this.importPackages = importPackages;
	}

	/**
	 * @return the moduleName
	 */
	public String getModuleName() {
		return moduleName;
	}

	/**
	 * @param moduleName
	 *            the moduleName to set
	 */
	public void setModuleName(String moduleName) {
		this.moduleName = moduleName;
	}

	/**
	 * @return the className
	 */
	public String getClassName() {
		return className;
	}

	/**
	 * @param className
	 *            the className to set
	 */
	public void setClassName(String className) {
		this.className = className;
	}

	/**
	 * @return the classComments
	 */
	public String getClassComments() {
		return classComments;
	}

	/**
	 * @param classComments
	 *            the classComments to set
	 */
	public void setClassComments(String classComments) {
		this.classComments = classComments;
	}

	/**
	 * @return the fields
	 */
	public String[] getFields() {
		return fields;
	}

	/**
	 * @param fields
	 *            the fields to set
	 */
	public void setFields(String[] fields) {
		this.fields = fields;
	}

	/**
	 * @return the fieldTypes
	 */
	public String[] getFieldTypes() {
		return fieldTypes;
	}

	/**
	 * @param fieldTypes
	 *            the fieldTypes to set
	 */
	public void setFieldTypes(String[] fieldTypes) {
		this.fieldTypes = fieldTypes;
	}

	/**
	 * @return the fieldComments
	 */
	public String[] getFieldComments() {
		return fieldComments;
	}

	/**
	 * @param fieldComments
	 *            the fieldComments to set
	 */
	public void setFieldComments(String[] fieldComments) {
		this.fieldComments = fieldComments;
	}

}
