package migzmigzmigz.com.calculator;

public final class Test {

    private Object subject;

    private Test (Object object) {
        subject = object;
    }

    public void is (int x) {
        if (subject instanceof Integer) {
            int subjectValue = (int) subject;
            if (subjectValue != x) {
                throw new AssertionError(subjectValue + " is not equal to " + x);
            }
        } else {
            throw new IllegalArgumentException("Test subject " + subject + " is not a valid integer to compare to " + x);
        }
    }

    public void is (boolean x) {
        if (subject instanceof Boolean) {
            boolean subjectValue = (boolean) subject;
            if (subjectValue != x) {
                throw new AssertionError(subjectValue + " is not equal to " + x);
            }
        } else {
            throw new IllegalArgumentException("Test subject " + subject + " is not a valid integer to compare to " + x);
        }
    }

    public static Test assertThat (Object object) {
        return new Test(object);
    }

}
