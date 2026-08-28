package einstein.engine;

/**
 * EinsteinEngine — real SR/GR calculations. Follows NEWTONS.
 * 
 * Implements special and general relativity equations using CODATA 2018 constants.
 */
public final class EinsteinEngine {

    // CODATA 2018 constants
    public static final double C = 299792458.0;              // speed of light (m/s)
    public static final double G = 6.67430e-11;              // gravitational constant (m³/(kg·s²))

    private EinsteinEngine() {
        // Utility class
    }

    /**
     * Lorentz factor γ = 1 / √(1 - v²/c²)
     * 
     * @param velocityMs velocity in m/s
     * @return Lorentz factor
     * @throws IllegalArgumentException if velocity >= c
     */
    public static double lorentzFactor(double velocityMs) {
        if (velocityMs >= C) {
            throw new IllegalArgumentException("velocity must be less than c, got " + velocityMs + " m/s");
        }
        double betaSquared = (velocityMs / C) * (velocityMs / C);
        return 1.0 / Math.sqrt(1.0 - betaSquared);
    }

    /**
     * Time dilation: t' = γ·t₀
     * 
     * @param properTimeS proper time in seconds
     * @param velocityMs velocity in m/s
     * @return dilated time in seconds
     */
    public static double timeDilation(double properTimeS, double velocityMs) {
        return lorentzFactor(velocityMs) * properTimeS;
    }

    /**
     * Mass–energy equivalence: E = mc²
     * 
     * @param massKg mass in kilograms
     * @return energy in joules
     * @throws IllegalArgumentException if mass is negative
     */
    public static double massEnergyEquivalence(double massKg) {
        if (massKg < 0.0) {
            throw new IllegalArgumentException("mass cannot be negative, got " + massKg);
        }
        return massKg * C * C;
    }

    /**
     * Relativistic momentum: p = γ·m·v
     * 
     * @param massKg mass in kilograms
     * @param velocityMs velocity in m/s
     * @return momentum in kg·m/s
     * @throws IllegalArgumentException if mass is negative
     */
    public static double relativisticMomentum(double massKg, double velocityMs) {
        if (massKg < 0.0) {
            throw new IllegalArgumentException("mass cannot be negative, got " + massKg);
        }
        return lorentzFactor(velocityMs) * massKg * velocityMs;
    }

    /**
     * Schwarzschild radius (non-rotating black hole): r_s = 2GM/c²
     * 
     * @param massKg mass in kilograms
     * @return Schwarzschild radius in meters
     * @throws IllegalArgumentException if mass is negative
     */
    public static double schwarzschildRadius(double massKg) {
        if (massKg < 0.0) {
            throw new IllegalArgumentException("mass cannot be negative, got " + massKg);
        }
        return 2.0 * G * massKg / (C * C);
    }

    public static void main(String[] args) {
        System.out.println("=== Einstein Engine ===");
        System.out.println();

        // Test: Lorentz factor at v = 0.8c
        double v = 0.8 * C;
        double gamma = lorentzFactor(v);
        System.out.printf("Lorentz factor at v=0.8c: %.6f%n", gamma);

        // Test: Sun Schwarzschild radius
        double sunMass = 1.989e30; // kg
        double sunRs = schwarzschildRadius(sunMass);
        System.out.printf("Sun Schwarzschild radius: %.1f m%n", sunRs);

        // Test: Earth Schwarzschild radius
        double earthMass = 5.972e24; // kg
        double earthRs = schwarzschildRadius(earthMass);
        System.out.printf("Earth Schwarzschild radius: %.2f mm%n", earthRs * 1000);

        // Test: Mass-energy equivalence for 1 kg
        double oneKgEnergy = massEnergyEquivalence(1.0);
        System.out.printf("1 kg → %.3e J%n", oneKgEnergy);
    }
}
