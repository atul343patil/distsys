import UpperModule.*;

class UpperImpl extends UpperPOA {

    public String to_uppercase(String str) {
        return str.toUpperCase();
    }
}