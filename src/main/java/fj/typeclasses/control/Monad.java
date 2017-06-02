package fj.typeclasses.control;

import fj.F;
import fj.Unit;
import fj.typeclasses.syntax.Do;
import org.derive4j.hkt.__;

public interface Monad<m> extends Applicative<m>, Bind<m> {

  default <A, B> __<m, B> liftM1(F<A, B> f, __<m, A> ma) {
    return Do.$(this
      , () -> ma
      , a -> pure(f.f(a)));
  }

  default <A, B> __<m, B> ap(__<m, F<A, B>> mf, __<m, A> ma) {
    return Do.$(this
      , () -> mf
      , __ -> ma
      , (f, a) -> pure(f.f(a)));
  }

  default __<m, Unit> whenM(__<m, Boolean> mb, __<m, Unit> m) {
    return Do.$(this
      , () -> mb
      , b -> when(b, m));
  }

  default __<m, Unit> unlessM(__<m, Boolean> mb, __<m, Unit> m) {
    return Do.$(this
      , () -> mb
      , b -> unless(b, m));
  }
}
