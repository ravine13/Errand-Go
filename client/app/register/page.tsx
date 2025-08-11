"use client"

import type React from "react"

import { useState } from "react"
import { Button, Input, Card, CardBody, CardHeader, Link, Select, SelectItem } from "@nextui-org/react"
import { Eye, EyeOff, Mail, Lock, User } from "lucide-react"
import { useAuth } from "@/contexts/AuthContext"
import { useRouter } from "next/navigation"
import NavigationBar from "@/components/Navbar"

export default function RegisterPage() {
  const [isVisible, setIsVisible] = useState(false)
  const [formData, setFormData] = useState({
    username: "",
    email: "",
    password: "",
    confirmPassword: "",
    accountType: "user",
  })
  const [isLoading, setIsLoading] = useState(false)
  const [error, setError] = useState("")

  const { register } = useAuth()
  const router = useRouter()

  const toggleVisibility = () => setIsVisible(!isVisible)

  const handleInputChange = (field: string, value: string) => {
    setFormData((prev) => ({ ...prev, [field]: value }))
  }

  const handleSubmit = async (e: React.FormEvent) => {
    e.preventDefault()
    setIsLoading(true)
    setError("")

    if (formData.password !== formData.confirmPassword) {
      setError("Passwords do not match")
      setIsLoading(false)
      return
    }

    // Adjust email based on account type for role assignment
    let email = formData.email
    if (formData.accountType === "errand_boy" && !email.includes("@errandboy.com")) {
      email = formData.username + "@errandboy.com"
    }

    const success = await register(formData.username, formData.password, email)

    if (success) {
      router.push("/login?message=Registration successful! Please log in.")
    } else {
      setError("Registration failed. Username or email may already exist.")
    }

    setIsLoading(false)
  }

  return (
    <div className="min-h-screen bg-gradient-to-br from-blue-50 to-purple-50">
      <NavigationBar />

      <div className="flex items-center justify-center min-h-[calc(100vh-80px)] px-4 py-8">
        <Card className="w-full max-w-md">
          <CardHeader className="flex flex-col gap-3 pb-6">
            <h1 className="text-2xl font-bold text-center">Join Errand GO</h1>
            <p className="text-gray-600 text-center">Create your account to get started</p>
          </CardHeader>

          <CardBody>
            <form onSubmit={handleSubmit} className="space-y-4">
              <Select
                label="Account Type"
                placeholder="Select account type"
                value={formData.accountType}
                onChange={(e) => handleInputChange("accountType", e.target.value)}
                isRequired
              >
                <SelectItem key="user" value="user">
                  Customer - I need tasks done
                </SelectItem>
                <SelectItem key="errand_boy" value="errand_boy">
                  Errand Boy - I want to complete tasks
                </SelectItem>
              </Select>

              <Input
                type="text"
                label="Username"
                placeholder="Choose a username"
                value={formData.username}
                onChange={(e) => handleInputChange("username", e.target.value)}
                startContent={<User className="w-4 h-4 text-gray-400" />}
                isRequired
              />

              <Input
                type="email"
                label="Email"
                placeholder="Enter your email"
                value={formData.email}
                onChange={(e) => handleInputChange("email", e.target.value)}
                startContent={<Mail className="w-4 h-4 text-gray-400" />}
                isRequired
              />

              <Input
                label="Password"
                placeholder="Create a password"
                value={formData.password}
                onChange={(e) => handleInputChange("password", e.target.value)}
                startContent={<Lock className="w-4 h-4 text-gray-400" />}
                endContent={
                  <button className="focus:outline-none" type="button" onClick={toggleVisibility}>
                    {isVisible ? (
                      <EyeOff className="w-4 h-4 text-gray-400" />
                    ) : (
                      <Eye className="w-4 h-4 text-gray-400" />
                    )}
                  </button>
                }
                type={isVisible ? "text" : "password"}
                isRequired
              />

              <Input
                label="Confirm Password"
                placeholder="Confirm your password"
                value={formData.confirmPassword}
                onChange={(e) => handleInputChange("confirmPassword", e.target.value)}
                startContent={<Lock className="w-4 h-4 text-gray-400" />}
                type={isVisible ? "text" : "password"}
                isRequired
              />

              {error && <p className="text-red-500 text-sm">{error}</p>}

              <Button type="submit" color="primary" size="lg" className="w-full" isLoading={isLoading}>
                Create Account
              </Button>
            </form>

            <div className="mt-6 text-center">
              <p className="text-gray-600">
                Already have an account?{" "}
                <Link href="/login" color="primary">
                  Sign in
                </Link>
              </p>
            </div>
          </CardBody>
        </Card>
      </div>
    </div>
  )
}
