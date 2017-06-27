package fj.data.bifunctor;

import fj.F;
import fj.Function;
import fj.data.Either;
import fj.data._FjData;
import org.derive4j.hkt.__;
import org.derive4j.hkt.__2;

public interface Bifunctor<f> {

  <A, B, C, D> __2<f, B, D> bimap(F<A, B> f, F<C, D> g, __<__<f, A>, C> fac);

  default <A, B, C> __2<f, B, C> lmap(F<A, B> f, __<__<f, A>, C> fac) {
    return bimap(f, Function.identity(), fac);
  }

  default <A, B, C> __2<f, A, C> rmap(F<B, C> f, __<__<f, A>, B> fab) {
    return bimap(Function.identity(), f, fab);
  }

  static EitherBifunctor either() { return () -> {}; }

  interface EitherBifunctor extends Bifunctor<Either.µ> {
    void self();

    @Override
    default <A, B, C, D> Either<B, D> bimap(F<A, B> f, F<C, D> g, __<__<Either.µ, A>, C> fac) {
      return _FjData
        .asEither(fac)
        .either(l -> Either.left(f.f(l)), r -> Either.right(g.f(r)));
    }

//    @Override
//    default <A, B, C> Either<B, C> lmap(F<A, B> f, __<__<Either.µ, A>, C> fac) {
//      return _FjData.asEither(Bifunctor.super.lmap(f, fac));
//    }
  }
}
