package fj.control;

import fj.F;
import fj.F0;
import fj.Function;
import fj.Unit;
import fj.data.Either;
import fj.data.List;
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

  static F0Applicative f0() { return () -> {}; }

  static <A> FApplicative<A> f() { return () -> {}; }

  static <A> EitherApplicative<A> either() { return () -> {}; }

  static ListApplicative list() { return () -> {}; }

  interface F0Applicative extends Applicative<F0.µ>, F0Apply {
    @Override
    default <A> F0<A> pure(A a) { return () -> a; }
  }

  interface FApplicative<X> extends Applicative<__<F.µ, X>>, FApply<X> {
    @Override
    default <A> F<X, A> pure(A a) { return Function.constant(a); }
  }

  interface EitherApplicative<X> extends Applicative<__<Either.µ, X>>, EitherApply<X> {
    @Override
    default <A> Either<X, A> pure(A a) { return Either.right(a); }
  }

  interface ListApplicative extends Applicative<List.µ>, ListApply {
    @Override
    default <A> List<A> pure(A a) { return List.single(a); }
  }
}
