package fj.control;

import fj.F;
import fj.F0;
import fj.P;
import fj._Fj;
import fj.data.Either;
import fj.data.List;
import fj.data._FjData;
import org.derive4j.hkt.__;

import static fj.Function.compose;
import static fj.Function.identity;

public interface Bind<m> extends Apply<m> {

  <A, B> __<m, B> bind(__<m, A> ma, F<A, __<m, B>> f);

  default <A, B> __<m, B> bindFlipped(F<A, __<m, B>> f, __<m, A> ma) {
    return bind(ma, f);
  }

  default <A> __<m, A> join(__<m, __<m, A>> ma) {
    return bind(ma, identity());
  }

  default <A, B, C> F<A, __<m, C>> composeK(F<A, __<m, B>> f, F<B, __<m, C>> g) {
    return a -> bind(f.f(a), g);
  }

  default <A, B, C> F<A, __<m, C>> composeKFlipped(F<B, __<m, C>> f, F<A, __<m, B>> g) {
    return composeK(g, f);
  }

  default <A> __<m, A> ifM(__<m, Boolean> cond, __<m, A> t, __<m, A> f) {
    return bind(cond, _cond -> _cond ? t : f);
  }

  static F0Bind f0() { return () -> {}; }

  static <A> FBind<A> f() { return () -> {}; }

  static <A> EitherBind<A> either() { return () -> {}; }

  static ListBind list() { return () -> {}; }

  interface F0Bind extends Bind<F0.µ>, F0Apply {
    @Override
    default  <A, B> F0<B> bind(__<F0.µ, A> ma, F<A, __<F0.µ, B>> f) {
      return P.lazy(_Fj.asF0(ma)).bind(a -> P.lazy(_Fj.asF0(f.f(a))));
    }
  }

  interface FBind<X> extends Bind<__<F.µ, X>>, FApply<X> {
    @Override
    default  <A, B> F<X, B> bind(__<__<F.µ, X>, A> ma, F<A, __<__<F.µ, X>, B>> f) {
      return x -> _Fj.asF(f.f(_Fj.asF(ma).f(x))).f(x);
    }
  }

  interface EitherBind<X> extends Bind<__<Either.µ, X>>, EitherApply<X> {
    @Override
    default <A, B> Either<X, B> bind(__<__<Either.µ, X>, A> ma, F<A, __<__<Either.µ, X>, B>> f) {
      return _FjData.asEither(ma).right().bind(compose(_FjData::asEither, f));
    }
  }

  interface ListBind extends Bind<List.µ>, ListApply {
    @Override
    default <A, B> List<B> bind(__<List.µ, A> ma, F<A, __<List.µ, B>> f) {
      return _FjData.asList(ma).bind(a -> _FjData.asList(f.f(a)));
    }
  }
}
