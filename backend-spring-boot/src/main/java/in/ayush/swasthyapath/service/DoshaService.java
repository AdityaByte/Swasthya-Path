package in.ayush.swasthyapath.service;

// Through this class we are calculating the dosha of the patient as per ayurvedic principles.

import in.ayush.swasthyapath.dto.PrakrutiAssessment;
import in.ayush.swasthyapath.dto.VikrutiAssessment;
import in.ayush.swasthyapath.enums.BodyType;
import in.ayush.swasthyapath.enums.DigestionStrength;
import in.ayush.swasthyapath.enums.Dosha;
import in.ayush.swasthyapath.enums.SkinType;
import org.springframework.stereotype.Service;

@Service
public class DoshaService {

    public Dosha calculatePrakruti(PrakrutiAssessment assessment) {
        int vata = 0, pitta = 0, kapha = 0;

        // Body Type scoring
        if (assessment.getBodyType() == BodyType.SLIM_LIGHT) vata += 2;
        else if (assessment.getBodyType() == BodyType.MEDIUM_WARM) pitta += 2;
        else if (assessment.getBodyType() == BodyType.SOLID_STEADY) kapha += 2;

        // Skin Nature scoring
        if (assessment.getSkinNature() == SkinType.DRY_ROUGH) vata += 1;
        else if (assessment.getSkinNature() == SkinType.WARM_OILY) pitta += 1;
        else if (assessment.getSkinNature() == SkinType.SOFT_COOL) kapha += 1;

        // Digestion scoring
        if (assessment.getDigestionStrength() == DigestionStrength.IRREGULAR) vata += 1;
        else if (assessment.getDigestionStrength() == DigestionStrength.STRONG) pitta += 1;
        else if (assessment.getDigestionStrength() == DigestionStrength.WEAK) kapha += 1;

        // Energy Pattern scoring
        if (assessment.getEnergyPattern().toLowerCase().contains("quick")) vata += 1;
        else if (assessment.getEnergyPattern().toLowerCase().contains("high")) pitta += 1;
        else if (assessment.getEnergyPattern().toLowerCase().contains("steady")) kapha += 1;

        // Sleep Nature scoring
        if (assessment.getSleepNature().toLowerCase().contains("light")) vata += 1;
        else if (assessment.getSleepNature().toLowerCase().contains("moderate")) pitta += 1;
        else if (assessment.getSleepNature().toLowerCase().contains("deep")) kapha += 1;

        // Determine dominant dosha
        if (vata >= pitta && vata >= kapha) return Dosha.VATA;
        else if (pitta >= vata && pitta >= kapha) return Dosha.PITTA;
        else return Dosha.KAPHA;
    }

    public Dosha calculateVikruti(VikrutiAssessment assessment) {
        int vata = 0, pitta = 0, kapha = 0;

        // Current Energy
        if (assessment.getCurrentEnergy().toLowerCase().contains("quick")) vata += 1;
        else if (assessment.getCurrentEnergy().toLowerCase().contains("high")) pitta += 1;
        else if (assessment.getCurrentEnergy().toLowerCase().contains("steady")) kapha += 1;

        // Current Sleep
        if (assessment.getCurrentSleep().toLowerCase().contains("light")) vata += 1;
        else if (assessment.getCurrentSleep().toLowerCase().contains("moderate")) pitta += 1;
        else if (assessment.getCurrentSleep().toLowerCase().contains("deep")) kapha += 1;

        // Digestion Today
        if (assessment.getDigestionToday().toLowerCase().contains("irregular")) vata += 1;
        else if (assessment.getDigestionToday().toLowerCase().contains("strong")) pitta += 1;
        else if (assessment.getDigestionToday().toLowerCase().contains("slow")) kapha += 1;

        // Current Concerns (stress, heaviness, acidity etc.)
        if (assessment.getCurrentConcerns().toLowerCase().contains("stress")) vata += 1;
        else if (assessment.getCurrentConcerns().toLowerCase().contains("anger") || assessment.getCurrentConcerns().toLowerCase().contains("heat")) pitta += 1;
        else if (assessment.getCurrentConcerns().toLowerCase().contains("heaviness") || assessment.getCurrentConcerns().toLowerCase().contains("slow")) kapha += 1;

        // Determine dominant dosha imbalance
        if (vata >= pitta && vata >= kapha) return Dosha.VATA;
        else if (pitta >= vata && pitta >= kapha) return Dosha.PITTA;
        else return Dosha.KAPHA;
    }

}
