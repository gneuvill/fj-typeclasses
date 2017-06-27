package fj.data.profunctor;

import fj.F;
import fj.Function;
import fj._Fj;
import org.derive4j.hkt.__;
import org.derive4j.hkt.__2;

/**
 * The {@link Closed} class extends the {@link Profunctor} class to work with functions.
 */
public interface Closed<p> extends Profunctor<p> {

  <A, B, X> __2<p, F<X, A>, F<X, B>> closed(__<__<p, A>, B> pab);

  static FClosed f() { return () -> {}; }

  interface FClosed extends Closed<F.µ>, FProfunctor {
    @Override
    default <A, B, X> F<F<X, A>, F<X, B>> closed(__<__<F.µ, A>, B> f) {
      return Function.<X, A, B>compose().f(_Fj.asF(f));
    }
  }
}
