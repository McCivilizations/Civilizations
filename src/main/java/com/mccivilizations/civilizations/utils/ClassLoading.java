package com.mccivilizations.civilizations.utils;

import com.mccivilizations.civilizations.Civilizations;
import com.mccivilizations.civilizations.api.civilization.Civilization;
import net.minecraftforge.fml.common.discovery.ASMDataTable;

import javax.annotation.Nonnull;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

public class ClassLoading {
    public static <T> List<T> getInstances(@Nonnull ASMDataTable asmDataTable, Class annotationClass, Class<T> instanceClass) {
        String annotationClassName = annotationClass.getCanonicalName();
        Set<ASMDataTable.ASMData> asmDataSet = asmDataTable.getAll(annotationClassName);
        List<T> instances = new ArrayList<>();
        for (ASMDataTable.ASMData asmData : asmDataSet) {
            try {
                Class<?> asmClass = Class.forName(asmData.getClassName());
                Class<? extends T> asmInstanceClass = asmClass.asSubclass(instanceClass);

                T instance = asmInstanceClass.newInstance();
                instances.add(instance);

            } catch (IllegalAccessException | InstantiationException | ClassNotFoundException exception) {
                Civilizations.logger.error(exception);
            }
        }
        return instances;
    }
}
