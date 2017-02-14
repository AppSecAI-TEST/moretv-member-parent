package org.mybatis.generator.extend.plugins;

import org.mybatis.generator.api.*;
import org.mybatis.generator.api.dom.java.CompilationUnit;
import org.mybatis.generator.api.dom.java.FullyQualifiedJavaType;
import org.mybatis.generator.api.dom.java.Interface;
import org.mybatis.generator.api.dom.java.JavaVisibility;
import org.mybatis.generator.exception.ShellException;
import org.mybatis.generator.extend.constant.GeneratorConstant;
import org.mybatis.generator.extend.util.GeneratorUtil;
import org.mybatis.generator.internal.DefaultShellCallback;
import org.mybatis.generator.internal.util.StringUtility;

import java.io.File;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

/**
 * 生成继承自GenericMapper的Mapper接口
 *
 * Created by Bob Jiang on 2017/2/13.
 */
public class GenericMapperGeneratorPlugin extends PluginAdapter {

    private String mapperTargetDir;
    private String mapperTargetPackage;

    private ShellCallback shellCallback = null;

    public GenericMapperGeneratorPlugin() {
        this.shellCallback = new DefaultShellCallback(false);
    }

    public boolean validate(List<String> list) {
        String mapperTargetDir = this.properties.getProperty("mapperTargetDir");
        this.mapperTargetDir = mapperTargetDir;
        String mapperTargetPackage = this.properties.getProperty("mapperTargetPackage");
        this.mapperTargetPackage = mapperTargetPackage;
        return StringUtility.stringHasValue(mapperTargetDir) && StringUtility.stringHasValue(mapperTargetPackage);
    }

    public List<GeneratedJavaFile> contextGenerateAdditionalJavaFiles(IntrospectedTable introspectedTable) {
        ArrayList<GeneratedJavaFile> mapperJavaFiles = new ArrayList<GeneratedJavaFile>();
        JavaFormatter javaFormatter = this.context.getJavaFormatter();

        List<IntrospectedColumn> primaryKeyColumns = introspectedTable.getPrimaryKeyColumns();
        FullyQualifiedJavaType pkType = !primaryKeyColumns.isEmpty() ?
                primaryKeyColumns.get(0).getFullyQualifiedJavaType() :
                new FullyQualifiedJavaType("java.lang.String");

        String packageName = introspectedTable.getFullyQualifiedTable().getSubPackage(true);

        Iterator<GeneratedJavaFile> javaFilesIterator = introspectedTable.getGeneratedJavaFiles().iterator();
        while (javaFilesIterator.hasNext()) {
            GeneratedJavaFile javaFile = javaFilesIterator.next();
            CompilationUnit unit = javaFile.getCompilationUnit();
            FullyQualifiedJavaType modelJavaType = unit.getType();
            String shortName = modelJavaType.getShortName();
            if(!shortName.endsWith("Example")) {
                String mapperName = shortName + "Mapper";
                Interface mapperInterface = new Interface(this.mapperTargetPackage + packageName + "." + mapperName);
                mapperInterface.setVisibility(JavaVisibility.PUBLIC);
                mapperInterface.addImportedType(modelJavaType);

                mapperInterface.addJavaDocLine("/**");
                mapperInterface.addJavaDocLine("* Mapper: " + mapperName);
                mapperInterface.addJavaDocLine("* Model : " + shortName);
                mapperInterface.addJavaDocLine("* Table : " + introspectedTable.getFullyQualifiedTable().getIntrospectedTableName());
                mapperInterface.addJavaDocLine("*");
                mapperInterface.addJavaDocLine("* This Mapper generated by MyBatis Generator Extend at " + GeneratorUtil.now());
                mapperInterface.addJavaDocLine("*/");

                FullyQualifiedJavaType superInterfaceType = new FullyQualifiedJavaType(GeneratorConstant.GENERIC_MAPPER_CLASS_PATH);
                mapperInterface.addImportedType(superInterfaceType);
                superInterfaceType.addTypeArgument(modelJavaType);
                superInterfaceType.addTypeArgument(pkType);
                mapperInterface.addSuperInterface(superInterfaceType);

                try {
                    GeneratedJavaFile file = new GeneratedJavaFile(mapperInterface, this.mapperTargetDir, javaFormatter);
                    File mapperDir = this.shellCallback.getDirectory(this.mapperTargetDir, this.mapperTargetPackage + packageName);
                    File mapperFile = new File(mapperDir, file.getFileName());
                    if(!mapperFile.exists()) {
                        mapperJavaFiles.add(file);
                    }
                } catch (ShellException e) {
                    e.printStackTrace();
                }
            }
        }
        return mapperJavaFiles;
    }

}
