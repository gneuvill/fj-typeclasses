package fj.typeclasses.control;

import fj.F;
import fj.Unit;
import org.derive4j.hkt.__;

public interface Applicative<f> extends Apply<f> {

  <A> __<f, A> pure(A a);

  default <A, B> __<f, B> liftA1(F<A, B> f, __<f, A> fa) {
    return apply(pure(f), fa);
  }

  default __<f, Unit> when(boolean b, __<f, Unit> m) {
    return b ? m : pure(Unit.unit());
  }

  default __<f, Unit> unless(boolean b, __<f, Unit> m) {
    return !b ? m : pure(Unit.unit());
  }
}
