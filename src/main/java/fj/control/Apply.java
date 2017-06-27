package fj.control;

import fj.*;
import fj.data.Either;
import fj.data.List;
import fj.data._FjData;
import fj.data.Functor;
import org.derive4j.hkt.__;

import static fj.Function.*;

public interface Apply<f> extends Functor<f> {

  <A, B> __<f, B> apply(__<f, F<A, B>> fab, __<f, A> fa);

  default <A, B> __<f, A> apFst(__<f, A> fa, __<f, B> fb) {
    return apply(map(constant(), fa), fb);
  }

  default <A, B> __<f, B> apSnd(__<f, A> fa, __<f, B> fb) {
    return apply(map(constant(identity()), fa), fb);
  }

  default <A, B, C> F2<__<f, A>, __<f, B>, __<f, C>> lift2(F2<A, B, C> f) {
    return (fa, fb) -> apply(map(curry(f), fa), fb);
  }

  default <A, B, C, D> F3<__<f, A>, __<f, B>, __<f, C>, __<f, D>> lift3(F3<A, B, C, D> f) {
    return (fa, fb, fc) -> apply(apply(map(curry(f), fa), fb), fc);
  }

  default <A, B, C, D, E> F4<__<f, A>, __<f, B>, __<f, C>, __<f, D>, __<f, E>> lift4(F4<A, B, C, D, E> f) {
    return (fa, fb, fc, fd) -> apply(apply(apply(map(curry(f), fa), fb), fc), fd);
  }

  default <A, B, C, D, E, F$> F5<__<f, A>, __<f, B>, __<f, C>, __<f, D>, __<f, E>, __<f, F$>> lift5(F5<A, B, C, D, E, F$> f) {
    return (fa, fb, fc, fd, fe) -> apply(apply(apply(apply(map(curry(f), fa), fb), fc), fd), fe);
  }

  static F0Apply f0() { return () -> {}; }

  static <A> FApply<A> f() { return () -> {}; }

  static <A> EitherApply<A> either() { return () -> {}; }

  static ListApply list() { return () -> {}; }

  interface F0Apply extends Apply<F0.µ>, F0Functor {
    @Override
    default  <A, B> F0<B> apply(__<F0.µ, F<A, B>> fab, __<F0.µ, A> fa) {
      return P.lazy(_Fj.asF0(fa)).apply(P.lazy(_Fj.asF0(fab)));
    }
  }

  interface FApply<X> extends Apply<__<F.µ, X>>, FFunctor<X> {
    @Override
    default  <A, B> F<X, B> apply(__<__<F.µ, X>, F<A, B>> fab, __<__<F.µ, X>, A> fa) {
      return x -> _Fj.asF(fab).f(x).f(_Fj.asF(fa).f(x));
    }
  }

  interface EitherApply<X> extends Apply<__<Either.µ, X>>, EitherFunctor<X> {
    @Override
    default  <A, B> Either<X, B> apply(__<__<Either.µ, X>, F<A, B>> fab, __<__<Either.µ, X>, A> fa) {
      return _FjData.asEither(fa).right().apply(_FjData.asEither(fab));
    }
  }

  interface ListApply extends Apply<List.µ>, ListFunctor {
    @Override
    default <A, B> List<B> apply(__<List.µ, F<A, B>> fab, __<List.µ, A> fa) {
      return _FjData.asList(fa).apply(_FjData.asList(fab));
    }
  }

//    interface Infix<f, A, B> {
//        __<f, F<A, B>> get(Apply<f> A);
//
//        default Infix<f, A, B> ᐸᚕᐳ(__<f, A> fb) {
//            return A -> A.apply(get(A), fb);
//        }
//
//        interface Builder<f, A> {
//            __<f, A> get(Apply<f> A);
//
//            default <B, C> Builder<f, F<B, C>> ᐸᚕᐳ(__<f, B> fb) {
//                return A -> A.apply(, get(A));
//            }
//        }
//    }
}
