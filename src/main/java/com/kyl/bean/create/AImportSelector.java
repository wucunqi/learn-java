package com.kyl.bean.create;

import org.springframework.context.annotation.ImportSelector;
import org.springframework.core.type.AnnotationMetadata;

import java.util.function.Predicate;

/**
 * @author wucunqi
 */
public class AImportSelector implements ImportSelector {

    @Override
    public String[] selectImports(AnnotationMetadata importingClassMetadata) {
        // 这个元数据是@Import注解标注的类的元数据
        String className = importingClassMetadata.getClassName();
        boolean b = importingClassMetadata.hasAnnotation("org.springframework.boot.autoconfigure.SpringBootApplication");
        return new String[0];
    }

    @Override
    public Predicate<String> getExclusionFilter() {
        return ImportSelector.super.getExclusionFilter();
    }
}
