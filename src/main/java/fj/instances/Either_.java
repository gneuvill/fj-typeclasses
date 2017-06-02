package fj.instances;

import fj.F;
import fj.data.Either;
import fj.data._FjData;
import fj.typeclasses.control.Monad;
import org.derive4j.hkt.__;

import static fj.Function.compose;

public final class Either_ {
  private Either_() {}

  @SuppressWarnings("unchecked")
  public static <A> Instances<A> instances() { return (Instances<A>) Instances.self; }

  public static final class Instances<X> implements Monad<__<Either.µ, X>> {
    private static final Instances<?> self = new Instances<>();
    private Instances() {}

    @Override
    public <A, B> Either<X, B> map(F<A, B> f, __<__<Either.µ, X>, A> fa) {
      return _FjData.asEither(fa).right().map(f);
    }

    @Override
    public <A> Either<X, A> pure(A a) {
      return Either.right(a);
    }

    @Override
    public <A, B> Either<X, B> bind(__<__<Either.µ, X>, A> ma, F<A, __<__<Either.µ, X>, B>> f) {
      return _FjData.asEither(ma).right().bind(compose(_FjData::asEither, f));
    }

    @Override
    public <A, B> Either<X, B> apply(__<__<Either.µ, X>, F<A, B>> fab, __<__<Either.µ, X>, A> fa) {
      return _FjData.asEither(fa).right().apply(_FjData.asEither(fab));
    }
  }
}
