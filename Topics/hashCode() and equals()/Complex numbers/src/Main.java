
class ComplexNumber {

    private final double re;
    private final double im;

    public ComplexNumber(double re, double im) {
        this.re = re;
        this.im = im;
    }

    public double getRe() {
        return re;
    }

    public double getIm() {
        return im;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof ComplexNumber that)) {
            return false;
        }
        return getRe() == that.getRe() && getIm() == that.getIm();
    }

    @Override
    public int hashCode() {
        int result = 42;
        result = (int) (31 * result + (Double.doubleToLongBits(getRe()) ^ (Double.doubleToLongBits(getRe()) >>> 32)));
        result = (int) (31 * result + (Double.doubleToLongBits(getIm()) ^ (Double.doubleToLongBits(getIm()) >>> 32)));
        return result;
    }
}