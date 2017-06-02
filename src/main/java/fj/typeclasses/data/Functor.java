package fj.typeclasses.data;

import fj.F;
import org.derive4j.hkt.__;

/**
 * A {@link Functor} is a type constructor which supports a mapping operation {@link #map}.
 *
 * <p>Instances must satisfy the following laws:</p>
 * <ul>
 * <li>Identity: <code>map id = id</code></li>
 * <li>Composition: <code>map (f . g) = map f . map g</code></li>
 * </ul>
 *
 * @param <f> type contructor that represents a computational context
 */
public interface Functor<f> {

  <A, B> __<f, B> map(F<A, B> f, __<f, A> fa);

  default <A, B> F<__<f, A>, __<f, B>> map_(F<A, B> f) {
    return fa -> map(f, fa);
  }

  default <A, B> __<f, B> mapFlipped(__<f, A> fa, F<A, B> f) {
    return map(f, fa);
  }

  default <A, B> F<F<A, B>, __<f, B>> mapFlipped_(__<f, A> fa) {
    return f -> map(f, fa);
  }

  static <f, A> Infix<f, A> _ᐧ(__<f, A> fa) { return F -> fa; }

  interface Infix<f, A> {
    __<f, A> ＿(Functor<f> F);

    default <B> Infix<f, B> ᐸ$ᐳ(F<A, B> f) {
      return F -> F.map(f, ＿(F));
    }
  }
}
