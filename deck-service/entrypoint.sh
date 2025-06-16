#!/bin/sh

# Ждём, пока profile-service не станет готов (с проверкой healthcheck)
until curl -sf http://profile-service:1000/actuator/health; do
  echo "⏳ Ждём profile-service..."
  sleep 5
done

echo "✅ profile-service доступен. Запускаем deck-service."

# Запуск основного приложения
exec java -jar app.jar