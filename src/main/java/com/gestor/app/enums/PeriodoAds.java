package com.gestor.app.enums;

public enum PeriodoAds {
	
	ALL_TIME("Desde o início"),
    TODAY("Hoje"),
    YESTERDAY("Ontem"),
    LAST_7_DAYS("Os últimos 7 dias não incluindo hoje"),
    LAST_BUSINESS_WEEK("Última semana útil(seg a sex)"),
    THIS_MONTH("Mês atual"),
    LAST_MONTH("Último mês"),
    LAST_14_DAYS("Os últimos 14 dias não incluindo hoje"),
    LAST_30_DAYS("Os últimos 30 dias não incluindo hoje"),
    PERSONALIZADO("Personalizado");
//    THIS_WEEK_SUN_TODAY("O período entre o domingo anterior e o dia atual"),
//    THIS_WEEK_MON_TODAY("O período entre a segunda-feira anterior e o dia atual"),
//    LAST_WEEK_SUN_SAT("O período de sete dias que começa no domingo anterior"),
//    LAST_WEEK_MON_SUN("O período de sete dias que começa na segunda-feira anterior");
    
    private final String description;
    
    private PeriodoAds(String description) {
        this.description = description;
    }
    
    public String getName() {
        return name();
    }
    
    public String getDescription() {
        return description;
    }
}
