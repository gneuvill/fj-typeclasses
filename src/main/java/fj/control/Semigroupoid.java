package fj.control;

import fj.F;
import fj.Function;
import fj._Fj;
import org.derive4j.hkt.__;
import org.derive4j.hkt.__2;

public interface Semigroupoid<a> {

  <B, C, D> __2<a, B, D> compose(__<__<a, C>, D> f, __<__<a, B>, C> g);

  default <B, C, D> __2<a, B, D> composeFlipped(__<__<a, B>, C> f, __<__<a, C>, D> g) {
    return compose(g, f);
  }

  static FSemigroupoid f() { return () -> {}; }

  interface FSemigroupoid extends Semigroupoid<F.µ> {
    void self();

    @Override
    default <B, C, D> F<B, D> compose(__<__<F.µ, C>, D> f, __<__<F.µ, B>, C> g) {
      return Function.compose(_Fj.asF(f), _Fj.asF(g));
    }
  }
}
