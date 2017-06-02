package fj.typeclasses.control;

import org.derive4j.hkt.__;
import org.derive4j.hkt.__2;

public interface Semigroupoid<a> {

  <B, C, D> __2<a, B, D> compose(__<__<a, C>, D> f, __<__<a, B>, C> g);

  default <B, C, D> __2<a, B, D> composeFlipped(__<__<a, B>, C> f, __<__<a, C>, D> g) {
    return compose(g, f);
  }
}
