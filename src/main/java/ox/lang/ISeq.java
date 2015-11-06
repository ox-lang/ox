package ox.lang;

/**
 * Created by arrdem on 11/2/15.
 *
 * Logically equivalent to Clojure's sequence interface.
 */
public interface ISeq<T> {
    T first();

    ISeq<T> rest();

    default T nth(int n) {
        ISeq<T> head = this;

        while(n != 0) {
            head = rest();
            n--;
        }

        if(head != null) {
            return head.first();
        } else {
            return null;
        }
    }
}
