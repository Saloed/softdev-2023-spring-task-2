public class Pair<F, S> {

    F first;
    S second;

    public Pair(F first, S second) {
        this.first = first;
        this.second = second;
    }


    public F getFirst() { return first; }
    public S getSecond(){
        return second;
    }



    @Override
    public String toString(){
        return first + " " + second;
    }

    @Override
    public boolean equals(Object o) {

        if (o == this) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        Pair<Integer, String> pair = (Pair<Integer, String>) o;
        if (this.first == pair.first && this.second.equals(pair.second)){
            return true;
        }

        else return false;

    }

    @Override
    public int hashCode() {
        return 31 * first.hashCode() + second.hashCode();
    }
}

