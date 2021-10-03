package com.tema;

public class DepartmentFactory {
    private static DepartmentFactory factory;

    private DepartmentFactory() {
    }

    public static DepartmentFactory getInstance() {
        if (factory == null)
            factory = new DepartmentFactory();
        return factory;
    }

    public Department createDepartment(String type) {
        switch (type) {
            case "IT" : return new IT();
            case "Management": return new Management();
            case "Marketing": return new Marketing();
            case "Finance": return new Finance();
            default: return null;
        }
    }
}
