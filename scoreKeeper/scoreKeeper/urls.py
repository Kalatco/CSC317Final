from django.contrib import admin
from django.urls import path
from django.conf.urls import include
from rest_framework.routers import SimpleRouter
from leaderboard.views import ScoreViewSet

router = SimpleRouter(trailing_slash=False)
router.register('scores', ScoreViewSet, basename='scores')

urlpatterns = [
    path('admin/', admin.site.urls),
    path('api/', include(router.urls)),
]
