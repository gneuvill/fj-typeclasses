package fj.control;

import fj.F;
import fj.F0;
import fj.Unit;
import fj.data.Either;
import fj.data.List;
import fj.syntax.Do;
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

  static F0Monad f0() { return () -> {}; }

  static <A> FMonad<A> f() { return () -> {}; }

  static <A> EitherMonad<A> either() { return () -> {}; }

  static ListMonad list() { return () -> {}; }

  interface F0Monad extends Monad<F0.µ>, F0Applicative, F0Bind {}

  interface FMonad<X> extends Monad<__<F.µ, X>>, FApplicative<X>, FBind<X> {}

  interface EitherMonad<X> extends Monad<__<Either.µ, X>>, EitherApplicative<X>, EitherBind<X> {}

  interface ListMonad extends Monad<List.µ>, ListApplicative, ListBind {}
}
