package org.nutrition.app.meal.service.statistics;

import org.nutrition.app.food.dto.NutritionCarbohydratesDTO;
import org.nutrition.app.food.dto.NutritionMineralsDTO;
import org.nutrition.app.food.dto.NutritionProximatesDTO;
import org.nutrition.app.food.dto.NutritionVitaminsDTO;
import org.nutrition.app.food.entity.NutritionCarbohydrates;
import org.nutrition.app.food.entity.NutritionMinerals;
import org.nutrition.app.food.entity.NutritionProximates;
import org.nutrition.app.food.entity.NutritionVitamins;

import java.math.BigDecimal;
import java.math.MathContext;


public class NutritionDtoAggregator {

    private NutritionDtoAggregator() {}

    private static double safe(Double value) {
        return value != null ? value : 0.0;
    }

    public static NutritionProximatesDTO emptyProximates() {
        return new NutritionProximatesDTO(null, null, null, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0);
    }

    public static NutritionCarbohydratesDTO emptyCarbohydrates() {
        return new NutritionCarbohydratesDTO(null, null, null, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0);
    }

    public static NutritionMineralsDTO emptyMinerals() {
        return new NutritionMineralsDTO(null, null, null, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0);
    }

    public static NutritionVitaminsDTO emptyVitamins() {
        return new NutritionVitaminsDTO(null, null, null, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0);
    }

    // ========== ADD ==========

    public static NutritionProximatesDTO add(NutritionProximatesDTO dto, NutritionProximates e) {
        return new NutritionProximatesDTO(
                dto.id(),
                dto.portionSize(),
                dto.unit(),
                dto.water() + safe(e.getWater()),
                dto.energyGeneral() + safe(e.getEnergyGeneral()),
                dto.energySpecific() + safe(e.getEnergySpecific()),
                dto.nitrogen() + safe(e.getNitrogen()),
                dto.protein() + safe(e.getProtein()),
                dto.totalLipid() + safe(e.getTotalLipid()),
                dto.ash() + safe(e.getAsh())
        );
    }

    public static NutritionCarbohydratesDTO add(NutritionCarbohydratesDTO dto, NutritionCarbohydrates e) {
        return new NutritionCarbohydratesDTO(
                dto.id(),
                dto.portionSize(),
                dto.unit(),
                dto.carbohydrate() + safe(e.getCarbohydrate()),
                dto.fiber() + safe(e.getFiber()),
                dto.totalSugars() + safe(e.getTotalSugars()),
                dto.sucrose() + safe(e.getSucrose()),
                dto.glucose() + safe(e.getGlucose()),
                dto.fructose() + safe(e.getFructose()),
                dto.maltose() + safe(e.getMaltose()),
                dto.lactose() + safe(e.getLactose())
        );
    }

    public static NutritionMineralsDTO add(NutritionMineralsDTO dto, NutritionMinerals e) {
        return new NutritionMineralsDTO(
                dto.id(),
                dto.portionSize(),
                dto.unit(),
                dto.calcium() + safe(e.getCalcium()),
                dto.iron() + safe(e.getIron()),
                dto.magnesium() + safe(e.getMagnesium()),
                dto.phosphorus() + safe(e.getPhosphorus()),
                dto.potassium() + safe(e.getPotassium()),
                dto.sodium() + safe(e.getSodium()),
                dto.zinc() + safe(e.getZinc()),
                dto.copper() + safe(e.getCopper()),
                dto.manganese() + safe(e.getManganese())
        );
    }

    public static NutritionVitaminsDTO add(NutritionVitaminsDTO dto, NutritionVitamins e) {
        return new NutritionVitaminsDTO(
                dto.id(),
                dto.portionSize(),
                dto.unit(),
                dto.vitaminA() + safe(e.getVitaminA()),
                dto.vitaminB1() + safe(e.getVitaminB1()),
                dto.vitaminB2() + safe(e.getVitaminB2()),
                dto.vitaminB3() + safe(e.getVitaminB3()),
                dto.vitaminB5() + safe(e.getVitaminB5()),
                dto.vitaminB6() + safe(e.getVitaminB6()),
                dto.vitaminB7() + safe(e.getVitaminB7()),
                dto.vitaminB9() + safe(e.getVitaminB9()),
                dto.vitaminB12() + safe(e.getVitaminB12()),
                dto.vitaminC() + safe(e.getVitaminC()),
                dto.vitaminD() + safe(e.getVitaminD()),
                dto.vitaminE() + safe(e.getVitaminE()),
                dto.vitaminK() + safe(e.getVitaminK())
        );
    }

    private static Double scale(Double value, BigDecimal factor) {
        if (value == null) return 0.0;
        return BigDecimal.valueOf(value).multiply(factor).doubleValue();
    }

    public static NutritionProximates scale(NutritionProximates np, BigDecimal gramsConsumed, BigDecimal gramsReference) {
        if (np == null) return null;
        BigDecimal factor = gramsConsumed.divide(gramsReference, MathContext.DECIMAL64);
        NutritionProximates scaled = new NutritionProximates();
        scaled.setWater(scale(np.getWater(), factor));
        scaled.setEnergyGeneral(scale(np.getEnergyGeneral(), factor));
        scaled.setEnergySpecific(scale(np.getEnergySpecific(), factor));
        scaled.setNitrogen(scale(np.getNitrogen(), factor));
        scaled.setProtein(scale(np.getProtein(), factor));
        scaled.setTotalLipid(scale(np.getTotalLipid(), factor));
        scaled.setAsh(scale(np.getAsh(), factor));
        return scaled;
    }

    public static NutritionCarbohydrates scale(NutritionCarbohydrates nc, BigDecimal gramsConsumed, BigDecimal gramsReference) {
        if (nc == null) return null;
        BigDecimal factor = gramsConsumed.divide(gramsReference, MathContext.DECIMAL64);
        NutritionCarbohydrates scaled = new NutritionCarbohydrates();
        scaled.setCarbohydrate(scale(nc.getCarbohydrate(), factor));
        scaled.setFiber(scale(nc.getFiber(), factor));
        scaled.setTotalSugars(scale(nc.getTotalSugars(), factor));
        scaled.setSucrose(scale(nc.getSucrose(), factor));
        scaled.setGlucose(scale(nc.getGlucose(), factor));
        scaled.setFructose(scale(nc.getFructose(), factor));
        scaled.setMaltose(scale(nc.getMaltose(), factor));
        scaled.setLactose(scale(nc.getLactose(), factor));
        return scaled;
    }

    public static NutritionMinerals scale(NutritionMinerals nm, BigDecimal gramsConsumed, BigDecimal gramsReference) {
        if (nm == null) return null;
        BigDecimal factor = gramsConsumed.divide(gramsReference, MathContext.DECIMAL64);
        NutritionMinerals scaled = new NutritionMinerals();
        scaled.setCalcium(scale(nm.getCalcium(), factor));
        scaled.setIron(scale(nm.getIron(), factor));
        scaled.setMagnesium(scale(nm.getMagnesium(), factor));
        scaled.setPhosphorus(scale(nm.getPhosphorus(), factor));
        scaled.setPotassium(scale(nm.getPotassium(), factor));
        scaled.setSodium(scale(nm.getSodium(), factor));
        scaled.setZinc(scale(nm.getZinc(), factor));
        scaled.setCopper(scale(nm.getCopper(), factor));
        scaled.setManganese(scale(nm.getManganese(), factor));
        return scaled;
    }

    public static NutritionVitamins scale(NutritionVitamins nv, BigDecimal gramsConsumed, BigDecimal gramsReference) {
        if (nv == null) return null;
        BigDecimal factor = gramsConsumed.divide(gramsReference, MathContext.DECIMAL64);
        NutritionVitamins scaled = new NutritionVitamins();
        scaled.setVitaminA(scale(nv.getVitaminA(), factor));
        scaled.setVitaminB1(scale(nv.getVitaminB1(), factor));
        scaled.setVitaminB2(scale(nv.getVitaminB2(), factor));
        scaled.setVitaminB3(scale(nv.getVitaminB3(), factor));
        scaled.setVitaminB5(scale(nv.getVitaminB5(), factor));
        scaled.setVitaminB6(scale(nv.getVitaminB6(), factor));
        scaled.setVitaminB7(scale(nv.getVitaminB7(), factor));
        scaled.setVitaminB9(scale(nv.getVitaminB9(), factor));
        scaled.setVitaminB12(scale(nv.getVitaminB12(), factor));
        scaled.setVitaminC(scale(nv.getVitaminC(), factor));
        scaled.setVitaminD(scale(nv.getVitaminD(), factor));
        scaled.setVitaminE(scale(nv.getVitaminE(), factor));
        scaled.setVitaminK(scale(nv.getVitaminK(), factor));
        return scaled;
    }
}