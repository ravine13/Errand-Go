"use client"

import { useAuth } from "@/contexts/AuthContext"
import { useRouter } from "next/navigation"
import { useEffect } from "react"
import NavigationBar from "@/components/Navbar"
import UserDashboard from "@/components/dashboard/UserDashboard"
import ErrandBoyDashboard from "@/components/dashboard/ErrandBoyDashboard"
import AdminDashboard from "@/components/dashboard/AdminDashboard"
import { Spinner } from "@nextui-org/react"

export default function DashboardPage() {
  const { user, isLoading } = useAuth()
  const router = useRouter()

  useEffect(() => {
    if (!isLoading && !user) {
      router.push("/login")
    }
  }, [user, isLoading, router])

  if (isLoading) {
    return (
      <div className="min-h-screen flex items-center justify-center">
        <Spinner size="lg" />
      </div>
    )
  }

  if (!user) {
    return null
  }

  const renderDashboard = () => {
    switch (user.role) {
      case "USER":
        return <UserDashboard />
      case "ERRAND_BOY":
        return <ErrandBoyDashboard />
      case "ADMIN":
        return <AdminDashboard />
      default:
        return <UserDashboard />
    }
  }

  return (
    <div className="min-h-screen bg-gray-50">
      <NavigationBar />
      {renderDashboard()}
    </div>
  )
}
