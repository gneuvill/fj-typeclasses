package fj.data;

import fj.F;
import fj.F0;
import fj.P2;
import fj._Fj;
import org.derive4j.hkt.__;

import static fj.Function.compose;

/**
 * A {@link Functor} is a type constructor which supports a mapping operation {@link #map}.
 *
 * <p>Instances must satisfy the following laws:</p>
 * <ul>
 * <li>Identity: <code>map id = id</code></li>
 * <li>Composition: <code>map (f . g) = map f . map g</code></li>
 * </ul>
 *
 * @param <f> type contructor that represents a computational context
 */
public interface Functor<f> {

  <A, B> __<f, B> map(F<A, B> f, __<f, A> fa);

  default <A, B> F<__<f, A>, __<f, B>> map_(F<A, B> f) {
    return fa -> map(f, fa);
  }

  default <A, B> __<f, B> mapFlipped(__<f, A> fa, F<A, B> f) {
    return map(f, fa);
  }

  default <A, B> F<F<A, B>, __<f, B>> mapFlipped_(__<f, A> fa) {
    return f -> map(f, fa);
  }

  static F0Functor f0() { return () -> {}; }

  static <A> FFunctor<A> f() { return () -> {}; }

  static <A> EitherFunctor<A> either() { return () -> {}; }

  static ListFunctor list() { return () -> {}; }

  static <A> P2Functor<A> p2() { return () -> {}; }

  interface F0Functor extends Functor<F0.µ> {
    void self();

    @Override
    default  <A, B> F0<B> map(F<A, B> f, __<F0.µ, A> fa) {
      return () -> f.f(_Fj.asF0(fa).f());
    }
  }

  interface FFunctor<X> extends Functor<__<F.µ, X>> {
    void self();

    @Override
    default  <A, B> F<X, B> map(F<A, B> f, __<__<F.µ, X>, A> fa) {
      return compose(f, _Fj.asF(fa));
    }
  }

  interface EitherFunctor<X>  extends Functor<__<Either.µ, X>> {
    void self();

    @Override
    default  <A, B> Either<X, B> map(F<A, B> f, __<__<Either.µ, X>, A> fa) {
      return _FjData.asEither(fa).right().map(f);
    }
  }

  interface ListFunctor extends Functor<List.µ> {
    void self();

    @Override
    default  <A, B> List<B> map(F<A, B> f, __<List.µ, A> fa) {
      return _FjData.asList(fa).map(f);
    }
  }

  interface P2Functor<X> extends Functor<__<P2.µ, X>> {
    void self();

    @Override
    default  <A, B> P2<X, B> map(F<A, B> f, __<__<P2.µ, X>, A> fa) {
      return _Fj.asP2(fa).map2(f);
    }
  }

  // syntax ?

  static <f, A> Infix<f, A> _ᐧ(__<f, A> fa) { return F -> fa; }

  interface Infix<f, A> {
    __<f, A> ＿(Functor<f> F);

    default <B> Infix<f, B> ᐸ$ᐳ(F<A, B> f) {
      return F -> F.map(f, ＿(F));
    }
  }
}
