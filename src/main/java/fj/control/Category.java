package fj.control;

import fj.F;
import fj.Function;
import org.derive4j.hkt.__2;

public interface Category<a> extends Semigroupoid<a> {

  <T> __2<a, T, T> id();

  static FCategory f() { return () -> {}; }

  interface FCategory extends Category<F.µ>, FSemigroupoid {
    @Override
    default <T> F<T, T> id() { return Function.identity(); }
  }
}
